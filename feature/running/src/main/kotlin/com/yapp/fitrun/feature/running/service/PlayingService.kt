package com.yapp.fitrun.feature.running.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.yapp.fitrun.core.domain.entity.LocationEntity
import com.yapp.fitrun.core.domain.repository.LocationRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class PlayingService : Service() {

    @Inject
    lateinit var fusedLocationClient: FusedLocationProviderClient

    @Inject
    lateinit var locationRepository: LocationRepository

    private lateinit var locationCallback: LocationCallback
    private val locationBuffer = mutableListOf<LocationEntity>()
    private var lastLocation: Location? = null

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var timerJob: Job? = null

    companion object {

        const val TAG = "PlayingService"

        const val ACTION_START = "ACTION_START"
        const val ACTION_RESUME = "ACTION_RESUME"
        const val ACTION_PAUSE = "ACTION_PAUSE"
        const val ACTION_STOP = "ACTION_STOP"

        private const val NOTIFICATION_CHANNEL_ID = "running_channel"
        private const val NOTIFICATION_ID = 1

        private const val LOCATION_UPDATE_INTERVAL = 2000L
        private const val LOCATION_BUFFER_SIZE = 100
        private const val TIMER_UPDATE_INTERVAL = 100L

        // State
        private val _isRunning = MutableStateFlow(false)
        val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

        private val _runningState = MutableStateFlow(RunningState.IDLE)
        val runningState: StateFlow<RunningState> = _runningState.asStateFlow()

        private val _runningData = MutableStateFlow(RunningData())
        val runningData: StateFlow<RunningData> = _runningData.asStateFlow()

        data class RunningData(
            val startTimestamp: Long = 0L, // 최초 시작 시간 (기록용)
            val elapsedTimeMillis: Long = 0L, // 실제 러닝 시간 (일시정지 제외)
            val distanceMeters: Float = 0f,
            val currentPaceMinPerKm: Float = 0f,
            val avgPaceMinPerKm: Float = 0f,
            val locations: List<LocationEntity> = emptyList(),
        )
    }

    enum class RunningState {
        IDLE,
        RUNNING, // 러닝 중
        PAUSED, // 일시정지
        STOPPED, // 정지
    }

    override fun onCreate() {
        super.onCreate()
        setupLocationCallback()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: $intent")
        when (intent?.action) {
            ACTION_START -> {
                if (_runningState.value == RunningState.IDLE) {
                    startService()
                }
            }

            ACTION_RESUME -> {
                if (_runningState.value == RunningState.PAUSED) {
                    resumeService()
                }
            }

            ACTION_PAUSE -> {
                if (_runningState.value == RunningState.RUNNING) {
                    pauseService()
                }
            }

            ACTION_STOP -> {
                if (_runningState.value != RunningState.STOPPED) {
                    stopService()
                }
            }
        }
        return START_NOT_STICKY
    }

    private fun startService() {
        Log.d(TAG, "startService: ")

        _isRunning.value = true
        _runningState.value = RunningState.RUNNING
        lastLocation = null
        locationBuffer.clear()

        // 시작 시간 기록 (현재 시간만 저장, 타이머는 0부터 시작)
        _runningData.value = RunningData(
            startTimestamp = System.currentTimeMillis(),
            elapsedTimeMillis = 0L,
        )

        // Foreground Service 시작
        startForeground(NOTIFICATION_ID, createNotification())
        // 타이머 시작
        startTimer()
        // 위치 추적 시작
        startLocationUpdates()
    }

    private fun pauseService() {
        _runningState.value = RunningState.PAUSED
        // 타이머 정지 (현재 시간은 유지됨)
        stopTimer()

        // 위치 추적 중지
        stopLocationUpdates()

        // 버퍼 데이터 저장
        serviceScope.launch {
            saveBufferedLocations()
        }
    }

    private fun resumeService() {
        _runningState.value = RunningState.RUNNING

        // 타이머 재시작 (이전 시간부터 계속)
        startTimer()

        // 위치 추적 재시작
        startLocationUpdates()
    }

    private fun stopService() {
        _isRunning.value = false
        _runningState.value = RunningState.STOPPED

        // 타이머 정지
        stopTimer()

        // 위치 추적 중지
        stopLocationUpdates()

        // 남은 위치 데이터 저장
        serviceScope.launch {
            saveBufferedLocations()

            // 초기화
            _runningData.value = RunningData()
            lastLocation = null
        }

        // 서비스 종료
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = serviceScope.launch {
            val currentElapsedTime = _runningData.value.elapsedTimeMillis
            val timerStartTime = System.currentTimeMillis() - currentElapsedTime

            while (isActive) {
                val elapsed = System.currentTimeMillis() - timerStartTime

                _runningData.value = _runningData.value.copy(
                    elapsedTimeMillis = elapsed,
                )

                // 페이스 재계산
                updatePace()

                delay(TIMER_UPDATE_INTERVAL)
            }
        }
    }

    private fun updatePace() {
        val currentData = _runningData.value

        // 평균 페이스 계산
        val avgPace = if (currentData.distanceMeters > 0 && currentData.elapsedTimeMillis > 0) {
            // 전체 시간(분) / 전체 거리(km)
            (currentData.elapsedTimeMillis / 1000f / 60f) / (currentData.distanceMeters / 1000f)
        } else {
            0f
        }

        _runningData.value = currentData.copy(
            avgPaceMinPerKm = if (avgPace.isFinite()) avgPace else 0f,
        )
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    private fun setupLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                if (_runningState.value == RunningState.RUNNING) {
                    result.lastLocation?.let { location ->
                        Log.d(TAG, "dsonLocationResult: $location")
                        processLocation(location)
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            LOCATION_UPDATE_INTERVAL,
        ).apply {
            setMinUpdateIntervalMillis(LOCATION_UPDATE_INTERVAL)
            setWaitForAccurateLocation(false)
        }.build()

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper(),
        )
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun processLocation(location: Location) {
        // LocationEntity 생성 및 버퍼에 추가
        val locationEntity = LocationEntity(
            lat = location.latitude,
            lng = location.longitude,
        )

        locationBuffer.add(locationEntity)

        // 거리 및 페이스 계산
        updateDistance(location)

        // 버퍼가 가득 찼으면 저장
        if (locationBuffer.size >= LOCATION_BUFFER_SIZE) {
            CoroutineScope(Dispatchers.IO).launch {
                saveBufferedLocations()
            }
        }
    }

    private fun updateDistance(newLocation: Location) {
        val currentData = _runningData.value

        // 거리 계산
        var newDistance = currentData.distanceMeters
        lastLocation?.let { last ->
            val distance = last.distanceTo(newLocation)
            // GPS 점프 방지
            if (distance < 200) {
                newDistance += distance
            }
        }
        lastLocation = newLocation

        // 현재 페이스 계산 (마지막 구간)
        val currentPace = if (newLocation.speed > 0.5) {
            (1000f / newLocation.speed) / 60f
        } else {
            0f
        }

        // 위치 리스트 업데이트
        val newLocations = currentData.locations.toMutableList()
        newLocations.add(
            LocationEntity(newLocation.latitude, newLocation.longitude),
        )
        // 상태 업데이트
        _runningData.value = currentData.copy(
            distanceMeters = newDistance,
            currentPaceMinPerKm = if (currentPace.isFinite()) currentPace else 0f,
            locations = newLocations,
        )
    }

    private suspend fun saveBufferedLocations() {
        if (locationBuffer.isNotEmpty()) {
            try {
                locationRepository.insertLocationList(locationBuffer.toList())
                locationBuffer.clear()
            } catch (e: IOException) {
                Log.e(TAG, "saveBufferedLocations: $e")
            }
        }
    }

    private fun createNotification(): android.app.Notification {
        // 채널 생성
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            "러닝 추적",
            NotificationManager.IMPORTANCE_LOW,
        ).apply {
            setShowBadge(false)
            setSound(null, null)
        }

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        // 앱으로 돌아가는 인텐트
//        val intent = Intent(this, MainActivity::class.java)
//        val pendingIntent = PendingIntent.getActivity(
//            this,
//            0,
//            intent,
//            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )

        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("FitRun")
            .setContentText("러닝 기록 중...")
            .setSmallIcon(com.yapp.fitrun.core.designsystem.R.drawable.ic_onboarding_check)
            .setOngoing(true)
            .setSilent(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
//            .setContentIntent(pendingIntent)
            .build()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
        stopLocationUpdates()
        serviceScope.cancel()
    }
}
