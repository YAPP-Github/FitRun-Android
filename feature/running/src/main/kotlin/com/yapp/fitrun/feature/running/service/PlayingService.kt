package com.yapp.fitrun.feature.running.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Build
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
import com.yapp.fitrun.core.domain.repository.RunningPoint
import com.yapp.fitrun.core.domain.repository.RunningRepository
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@AndroidEntryPoint
class PlayingService : Service() {

    @Inject
    lateinit var fusedLocationClient: FusedLocationProviderClient

    @Inject
    lateinit var locationRepository: LocationRepository

    @Inject
    lateinit var runningRepository: RunningRepository

    private lateinit var locationCallback: LocationCallback
    private val locationBuffer = mutableListOf<LocationEntity>()
    private var lastLocation: Location? = null

    // 러닝 기록 ID
    private var recordId: Int? = null

    // 러닝 시작 시간 (ISO 8601 형식)
    private var startTimeStamp: String? = null

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
        private const val LOCATION_BUFFER_SIZE = 10 // 버퍼 크기를 줄여서 더 자주 저장
        private const val TIMER_UPDATE_INTERVAL = 100L
        private const val MAX_MEMORY_LOCATIONS = 50 // 메모리에 유지할 최대 위치 개수

        // State
        private val _isRunning = MutableStateFlow(false)
        val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

        private val _runningState = MutableStateFlow(RunningState.IDLE)
        val runningState: StateFlow<RunningState> = _runningState.asStateFlow()

        private val _runningData = MutableStateFlow(RunningData())
        val runningData: StateFlow<RunningData> = _runningData.asStateFlow()

        // 러닝 기록 ID (서비스 외부에서 접근 가능)
        private val _recordId = MutableStateFlow<Int?>(null)
        val recordId: StateFlow<Int?> = _recordId.asStateFlow()

        data class RunningData(
            val startTimestamp: Long = 0L, // 최초 시작 시간 (기록용)
            val elapsedTimeMillis: Long = 0L, // 실제 러닝 시간 (일시정지 제외)
            val distanceMeters: Float = 0f,
            val currentPaceMinPerKm: Float = 0f,
            val avgPaceMinPerKm: Float = 0f,
            val recentLocations: List<LocationEntity> = emptyList(), // 최근 위치만 유지 (UI 표시용)
            val totalLocationCount: Int = 0, // 전체 위치 개수
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

        // 시작 시간 기록
        val currentTime = System.currentTimeMillis()
        startTimeStamp = getIso8601TimeStamp(currentTime)

        _runningData.value = RunningData(
            startTimestamp = currentTime,
            elapsedTimeMillis = 0L,
        )

        // 이전 위치 데이터 초기화
        serviceScope.launch {
            try {
                locationRepository.clearAll()
                Log.d(TAG, "Cleared all previous locations")
            } catch (e: Exception) {
                Log.e(TAG, "Error clearing locations", e)
            }
        }

        // Foreground Service 시작
        startForeground(NOTIFICATION_ID, createNotification())

        // 현재 위치를 가져와서 러닝 시작 API 호출
        requestRunningStart()

        // 타이머 시작
        startTimer()
        // 위치 추적 시작
        startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun requestRunningStart() {
        serviceScope.launch {
            try {
                // 현재 위치 가져오기
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let {
                        serviceScope.launch {
                            try {
                                val result = runningRepository.setRunningStart(
                                    lat = it.latitude,
                                    lon = it.longitude,
                                    timeStamp = startTimeStamp ?: getIso8601TimeStamp()
                                )

                                result.onSuccess { entity ->
                                    recordId = entity.recordId
                                    _recordId.value = entity.recordId
                                    Log.d(TAG, "Running start success. RecordId: ${entity.recordId}")
                                }.onFailure { exception ->
                                    Log.e(TAG, "Failed to start running", exception)
                                }
                            } catch (e: Exception) {
                                Log.e(TAG, "Error calling setRunningStart", e)
                            }
                        }
                    } ?: run {
                        Log.w(TAG, "Last location is null, waiting for first location update")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error getting last location", e)
            }
        }
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
        Log.d(TAG, "stopService: Starting stop process")

        _isRunning.value = false
        _runningState.value = RunningState.STOPPED

        // 타이머 정지
        stopTimer()

        // 위치 추적 중지
        stopLocationUpdates()

        // 현재 러닝 데이터를 저장
        val finalRunningData = _runningData.value

        // 남은 위치 데이터를 동기적으로 저장하고 러닝 완료 API 호출
        runBlocking {
            try {
                // 버퍼에 남은 위치들 저장
                saveBufferedLocations()

                // 러닝 완료 API 호출
                recordId?.let { id ->
                    sendRunningComplete(id, finalRunningData)
                }

                Log.d(TAG, "stopService: Successfully saved all data and sent complete request")
            } catch (e: Exception) {
                Log.e(TAG, "stopService: Error saving data", e)
            }
        }

        // 초기화
        _runningData.value = RunningData()
        _recordId.value = null
        recordId = null
        startTimeStamp = null
        lastLocation = null
        locationBuffer.clear()

        // 서비스 종료
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private suspend fun sendRunningComplete(recordId: Int, runningData: RunningData) {
        try {
            // DB에서 모든 위치 데이터 가져오기
            val allLocations = locationRepository.getLocations().first()

            // RunningPoint로 변환
            val runningPoints = allLocations.mapIndexed { index, location ->
                RunningPoint(
                    lat = location.lat,
                    lon = location.lng,
                    timeStamp = getIso8601TimeStamp(
                        runningData.startTimestamp + (index * LOCATION_UPDATE_INTERVAL)
                    ),
                    totalRunningTimeMills = (index * LOCATION_UPDATE_INTERVAL).toInt()
                )
            }

            // 평균 페이스 계산 (초 단위)
            val avgPaceSeconds = if (runningData.avgPaceMinPerKm > 0) {
                (runningData.avgPaceMinPerKm * 60).toInt()
            } else 0

            // 총 칼로리 계산
            val totalCalories = calculateCalories(
                runningData.distanceMeters / 1000f,
                runningData.elapsedTimeMillis / 1000 / 60 // 분 단위
            )

            val result = runningRepository.setRunningComplete(
                recordId = recordId,
                averagePace = avgPaceSeconds,
                runningPoints = runningPoints,
                startAt = startTimeStamp ?: getIso8601TimeStamp(runningData.startTimestamp),
                totalCalories = totalCalories,
                totalDistance = (runningData.distanceMeters / 1000f).toDouble(),
                totalTime = (runningData.elapsedTimeMillis / 1000).toInt()
            )

            result.onSuccess {
                Log.d(TAG, "Running complete success with ${runningPoints.size} points")
            }.onFailure { exception ->
                Log.e(TAG, "Failed to complete running", exception)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error calling setRunningComplete", e)
        }
    }

    private fun calculateCalories(distanceKm: Float, timeMinutes: Long): Int {
        // 간단한 칼로리 계산 공식 (체중 70kg 기준)
        val weight = 70f // kg
        val met = 9.0f // 러닝의 MET 값
        return (met * weight * timeMinutes / 60f).toInt()
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
                        Log.d(TAG, "onLocationResult: $location")
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
        // LocationEntity 생성
        val locationEntity = LocationEntity(
            lat = location.latitude,
            lng = location.longitude,
        )

        // 버퍼에 추가
        locationBuffer.add(locationEntity)

        // 거리 및 페이스 계산
        updateDistance(location, locationEntity)

        // 버퍼가 가득 찼으면 저장
        if (locationBuffer.size >= LOCATION_BUFFER_SIZE) {
            serviceScope.launch {
                saveBufferedLocations()
            }
        }
    }

    private fun updateDistance(newLocation: Location, locationEntity: LocationEntity) {
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

        // 최근 위치 리스트 업데이트 (UI 표시용, 최대 개수 제한)
        val newRecentLocations = currentData.recentLocations.toMutableList()
        newRecentLocations.add(locationEntity)
        if (newRecentLocations.size > MAX_MEMORY_LOCATIONS) {
            newRecentLocations.removeAt(0)
        }

        // 상태 업데이트
        _runningData.value = currentData.copy(
            distanceMeters = newDistance,
            currentPaceMinPerKm = if (currentPace.isFinite()) currentPace else 0f,
            recentLocations = newRecentLocations,
            totalLocationCount = currentData.totalLocationCount + 1,
        )
    }

    private suspend fun saveBufferedLocations() {
        if (locationBuffer.isNotEmpty()) {
            try {
                withContext(Dispatchers.IO) {
                    val locationsToSave = locationBuffer.toList()
                    locationRepository.insertLocationList(locationsToSave)
                    Log.d(TAG, "saveBufferedLocations: Saved ${locationsToSave.size} locations")
                    locationBuffer.clear()
                }
            } catch (e: IOException) {
                Log.e(TAG, "saveBufferedLocations: Failed to save locations", e)
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

        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("FitRun")
            .setContentText("러닝 기록 중...")
            .setSmallIcon(com.yapp.fitrun.core.designsystem.R.mipmap.ic_fitrun_logo)
            .setOngoing(true)
            .setSilent(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()

        // onDestroy에서도 안전장치로 위치 저장 시도
        runBlocking {
            try {
                saveBufferedLocations()
            } catch (e: Exception) {
                Log.e(TAG, "onDestroy: Failed to save locations", e)
            }
        }

        stopTimer()
        stopLocationUpdates()
        serviceScope.cancel()
    }

    // ISO 8601 형식의 타임스탬프 생성
    private fun getIso8601TimeStamp(timeMillis: Long = System.currentTimeMillis()): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Instant.ofEpochMilli(timeMillis)
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        } else {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
            sdf.timeZone = TimeZone.getDefault()
            sdf.format(Date(timeMillis))
        }
    }
}