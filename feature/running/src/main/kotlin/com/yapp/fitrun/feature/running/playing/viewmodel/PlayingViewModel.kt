package com.yapp.fitrun.feature.running.playing.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.fitrun.core.domain.repository.AudioRepository
import com.yapp.fitrun.core.domain.repository.GoalRepository
import com.yapp.fitrun.feature.running.audio.AudioPlayer
import com.yapp.fitrun.feature.running.service.PlayingService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PlayingViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val audioRepository: AudioRepository,
    private val audioCoachPlayer: AudioPlayer,
    private val goalRepository: GoalRepository,
) : ViewModel(), ContainerHost<PlayingState, PlayingSideEffect> {

    override val container: Container<PlayingState, PlayingSideEffect> = container(
        PlayingState(
            runningState = PlayingService.RunningState.IDLE,
        ),
    )

    // 페이스 UI 갱신용 Flow
    private val _displayedPace = MutableStateFlow("--'--\"")
    val displayedPace: StateFlow<String> = _displayedPace.asStateFlow()

    private var paceUpdateJob: Job? = null
    private var lastPaceUpdate = 0L
    private val PACE_UPDATE_INTERVAL = 1000L // 1초

    // 오디오 코칭 관련
    private var audioCoachingJob: Job? = null

    // 피드백 간격 설정 (밀리초)
//    private val COACH_INTERVAL = 60000L // 1분
//    private val RUNNING_INFO_INTERVAL = 300000L // 5분
    private val PACE_FEEDBACK_INTERVAL = 180000L // 3분
//    private val TIME_FEEDBACK_INTERVAL = 600000L // 10분

    // 마지막 피드백 시간 추적
//    private var lastCoachTime = 0L
//    private var lastRunningInfoTime = 0L
    private var lastDistanceFeedback = 0f
    private var lastPaceFeedbackTime = 0L
    private var hasPlayedHalfTime = false
    private var lastCoachDistanceKm = 0
    //    private var lastTimeFeedbackTime = 0L

    // 목표 설정 (실제로는 Goal Repository에서 가져와야 함)
    private var goalPace: Float? = null // 분/km
    private var goalDistance: Float? = null // km
    private var goalTime: Int? = null // 분

    init {
        getGoal()
        observeServiceState()
        observeRecordId()
        startServiceAutomatically()
        preloadCoachAudio() // 코치 오디오 미리 로드
    }

    private fun getGoal() = intent {
        goalRepository.getGoal()
            .onSuccess { goalEntity ->
                reduce {
                    state.copy(
                        paceGoal = goalEntity.paceGoal,
                        distanceMeterGoal = goalEntity.distanceMeterGoal,
                        timeGoal = goalEntity.timeGoal,
                        runnerType = goalEntity.runnerType,
                    )
                }

                // 클래스 레벨 변수도 업데이트 (오디오 피드백용)
                goalEntity.paceGoal?.let {
                    goalPace = it / 1000f / 60f // 밀리초를 분으로 변환
                }
                goalEntity.distanceMeterGoal?.let {
                    goalDistance = it.toFloat() / 1000f // 미터를 킬로미터로 변환
                }
                goalEntity.timeGoal?.let {
                    goalTime = (it / 1000 / 60).toInt() // 밀리초를 분으로 변환
                }
            }
            .onFailure { throwable ->
                // 에러 처리 - 필요시 로깅이나 사용자에게 알림
                Log.e("PlayingViewModel", "Failed to get goal", throwable)
            }
    }

    private fun observeServiceState() = intent {
        combine(
            PlayingService.runningState,
            PlayingService.runningData,
        ) { runningState, runningData ->
            Pair(runningState, runningData)
        }.collect { (runningState, runningData) ->
            reduce {
                state.copy(
                    runningState = runningState,
                    elapsedTimeMillis = runningData.elapsedTimeMillis,
                    distanceKm = runningData.distanceMeters / 1000f,
                    avgPaceMinPerKm = runningData.avgPaceMinPerKm,
                    currentSpeedKmh = if (runningData.currentPaceMinPerKm > 0) {
                        60f / runningData.currentPaceMinPerKm
                    } else 0f,
                    formattedTime = formatTime(runningData.elapsedTimeMillis),
                    formattedDistance = String.format(
                        Locale.getDefault(),
                        "%.2f",
                        runningData.distanceMeters / 1000f,
                    ),
                    formattedAvgPace = formatPace(runningData.avgPaceMinPerKm),
                    recentLocations = runningData.recentLocations,
                    totalLocationCount = runningData.totalLocationCount,
                )
            }

            // 페이스 UI 갱신 제어
            updatePaceDisplay(runningData.avgPaceMinPerKm)

            // 러닝 상태에 따른 오디오 코칭 제어
            when (runningState) {
                PlayingService.RunningState.RUNNING -> {
                    checkAndPlayAudioFeedback(runningData)
                    startPaceUpdateJob()
                }

                PlayingService.RunningState.PAUSED -> {
                    stopAudioCoaching()
                    stopPaceUpdateJob()
                }

                PlayingService.RunningState.STOPPED -> {
                    stopAudioCoaching()
                    stopPaceUpdateJob()
                    // 러닝이 종료되면 결과 화면으로 이동
//                    if (state.recordId != null) {
//                        postSideEffect(PlayingSideEffect.NavigateToResult(state.recordId!!))
//                    }
                }

                else -> {}
            }
        }
    }

    private fun observeRecordId() = intent {
        PlayingService.recordId.collect { recordId ->
            Log.d("PlayingViewModel", "Received recordId: $recordId")
            reduce {
                state.copy(recordId = recordId)
            }

            // recordId를 받으면 UI에 알림
            recordId?.let {
                postSideEffect(PlayingSideEffect.RunningStarted(it))
            }
        }
    }

    private fun checkAndPlayAudioFeedback(runningData: PlayingService.Companion.RunningData) =
        intent {
            if (!state.isVolumeOn) return@intent

            val currentTime = runningData.elapsedTimeMillis
            val currentDistance = runningData.distanceMeters / 1000f
            val currentPace = runningData.avgPaceMinPerKm

            audioCoachingJob?.cancel()
            audioCoachingJob = viewModelScope.launch {
                // 1. 코치 음성 (동기부여)
                checkAndPlayCoachAudio(currentDistance)

                // 2. 목표 설정에 따른 피드백 우선순위 적용
                val hasDistanceGoal = state.distanceMeterGoal != null
                val hasTimeGoal = state.timeGoal != null
                val hasPaceGoal = state.paceGoal != null

                when {
                    // 거리 & 시간 & 페이스 목표가 모두 설정된 경우: 페이스 피드백만
                    hasDistanceGoal && hasTimeGoal && hasPaceGoal -> {
                        checkAndPlayPaceFeedback(currentPace, currentTime)
                    }
                    // 거리 & 시간 목표 설정: 페이스 피드백
                    hasDistanceGoal && hasTimeGoal -> {
                        checkAndPlayPaceFeedback(currentPace, currentTime)
                    }
                    // 거리 & 페이스 목표 설정: 거리 & 페이스 피드백
                    hasDistanceGoal && hasPaceGoal -> {
                        checkAndPlayDistanceFeedback(currentDistance)
                        delay(1000) // 피드백 간 딜레이
                        checkAndPlayPaceFeedback(currentPace, currentTime)
                    }
                    // 시간 & 페이스 목표 설정: 시간 & 페이스 피드백
                    hasTimeGoal && hasPaceGoal -> {
                        checkAndPlayTimeFeedback(currentTime)
                        delay(1000) // 피드백 간 딜레이
                        checkAndPlayPaceFeedback(currentPace, currentTime)
                    }
                    // 페이스 목표만 설정: 페이스 피드백
                    hasPaceGoal -> {
                        checkAndPlayPaceFeedback(currentPace, currentTime)
                    }
                    // 거리 목표만 설정: 거리 피드백
                    hasDistanceGoal -> {
                        checkAndPlayDistanceFeedback(currentDistance)
                    }
                    // 시간 목표만 설정: 시간 피드백
                    hasTimeGoal -> {
                        checkAndPlayTimeFeedback(currentTime)
                    }
                }
            }
        }

    private suspend fun checkAndPlayCoachAudio(currentDistance: Float) = intent {
        val distanceKm = currentDistance.toInt()

        // runnerType에 따라 코치 오디오 재생 간격 결정
        val shouldPlayCoach = when (state.runnerType) {
            "BEGINNER" -> distanceKm > 0 && distanceKm % 1 == 0 && distanceKm != lastCoachDistanceKm // 1km마다
            "INTERMEDIATE" -> distanceKm > 0 && distanceKm % 3 == 0 && distanceKm != lastCoachDistanceKm // 3km마다
            "EXPERT" -> distanceKm > 0 && distanceKm % 5 == 0 && distanceKm != lastCoachDistanceKm // 5km마다
            else -> false
        }

        if (shouldPlayCoach) {
            playCoachAudio()
            lastCoachDistanceKm = distanceKm
        }
    }

    private suspend fun checkAndPlayDistanceFeedback(currentDistance: Float) = intent {
        if (state.distanceMeterGoal == null) return@intent

        val goalDistanceKm = state.distanceMeterGoal!! / 1000f
        val distanceKm = currentDistance.toInt()

        // 1km마다 피드백
        if (distanceKm > lastDistanceFeedback.toInt()) {
            val type = when {
                currentDistance >= goalDistanceKm -> "DISTANCE_FINISH" // 목표 완주
                goalDistanceKm - currentDistance <= 1f -> "DISTANCE_LEFT_1KM" // 마지막 1km
                else -> when (distanceKm) {
                    1 -> "DISTANCE_1KM"
                    2 -> "DISTANCE_2KM"
                    3 -> "DISTANCE_3KM"
                    else -> "DISTANCE_PASS_${distanceKm}KM"
                }
            }

            playDistanceFeedback(type)
            lastDistanceFeedback = distanceKm.toFloat()
        }
    }

    private suspend fun checkAndPlayPaceFeedback(currentPace: Float, currentTime: Long) = intent {
        if (state.paceGoal == null) return@intent
        if (currentTime - lastPaceFeedbackTime < PACE_FEEDBACK_INTERVAL) return@intent

        val goalPaceMinPerKm = state.paceGoal!! / 1000f / 60f
        val paceDiff = currentPace - goalPaceMinPerKm

        val type = when {
            paceDiff < -0.25f -> "PACE_FAST" // 15초/km 이상 빠름
            paceDiff > 0.5f -> "PACE_SLOW" // 30초/km 이상 느림
            kotlin.math.abs(paceDiff) <= 0.1f -> "PACE_GOOD" // 목표 페이스 유지
            else -> return@intent
        }

        playPaceFeedback(type)
        lastPaceFeedbackTime = currentTime
    }

    private suspend fun checkAndPlayTimeFeedback(currentTime: Long) = intent {
        if (state.timeGoal == null) return@intent

        val elapsedMinutes = currentTime / 1000 / 60
        val goalMinutes = state.timeGoal!! / 1000 / 60

        val type = when {
            elapsedMinutes >= goalMinutes -> "TIME_FINISH" // 목표 시간 도달
            goalMinutes - elapsedMinutes <= 5 -> "TIME_LEFT_5MIN" // 5분 전
            elapsedMinutes >= goalMinutes / 2 && !hasPlayedHalfTime -> {
                hasPlayedHalfTime = true
                "TIME_PASS_HALF" // 50% 지점
            }

            else -> return@intent
        }

        playTimeFeedback(type)
    }

    private fun updatePaceDisplay(avgPaceMinPerKm: Float) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastPaceUpdate >= PACE_UPDATE_INTERVAL) {
            _displayedPace.value = formatPace(avgPaceMinPerKm)
            lastPaceUpdate = currentTime
        }
    }

    private fun startPaceUpdateJob() = intent {
        if (paceUpdateJob?.isActive == true) return@intent

        paceUpdateJob = viewModelScope.launch {
            while (true) {
                delay(PACE_UPDATE_INTERVAL)
                _displayedPace.value = formatPace(state.avgPaceMinPerKm)
            }
        }
    }

    private fun stopPaceUpdateJob() {
        paceUpdateJob?.cancel()
        paceUpdateJob = null
    }

    private fun preloadCoachAudio() = intent {
        viewModelScope.launch {
            audioRepository.getCoach().fold(
                onSuccess = { audioEntity ->
                    // 코치 오디오 미리 로드
                    reduce {
                        state.copy(preloadedCoachAudio = audioEntity.audioData)
                    }
                },
                onFailure = {
                    // 에러 처리
                },
            )
        }
    }

    private fun playCoachAudio() = intent {
        val audioData = state.preloadedCoachAudio ?: run {
            audioRepository.getCoach().getOrNull()?.audioData
        }

        audioData?.let {
            if (state.isVolumeOn && state.runningState == PlayingService.RunningState.RUNNING) {
                audioCoachPlayer.playWithMediaPlayer(it)
            }
        }
    }

//    private fun playRunningInfo(currentPace: Float) = intent {
//        val paceMillis = (currentPace * 60 * 1000).toInt().toString()
//
//        audioRepository.getRunningInfo(paceMillis).fold(
//            onSuccess = { audioEntity ->
//                if (state.isVolumeOn && state.runningState == PlayingService.RunningState.RUNNING) {
//                    audioCoachPlayer.playWithMediaPlayer(audioEntity.audioData)
//                }
//            },
//            onFailure = {
//                // 에러 처리
//            },
//        )
//    }

    private fun playDistanceFeedback(type: String) = intent {
        audioRepository.getDistanceFeedback(type).fold(
            onSuccess = { audioEntity ->
                if (state.isVolumeOn && state.runningState == PlayingService.RunningState.RUNNING) {
                    audioCoachPlayer.playWithMediaPlayer(audioEntity.audioData)
                }
            },
            onFailure = { exception ->
                Log.e("PlayingViewModel", "Failed to play distance feedback", exception)
            },
        )
    }

    private fun playPaceFeedback(type: String) = intent {
        audioRepository.getPaceFeedback(type).fold(
            onSuccess = { audioEntity ->
                if (state.isVolumeOn && state.runningState == PlayingService.RunningState.RUNNING) {
                    audioCoachPlayer.playWithMediaPlayer(audioEntity.audioData)
                }
            },
            onFailure = { exception ->
                Log.e("PlayingViewModel", "Failed to play pace feedback", exception)
            },
        )
    }

    private fun playTimeFeedback(type: String) = intent {
        audioRepository.getTimeFeedback(type).fold(
            onSuccess = { audioEntity ->
                if (state.isVolumeOn && state.runningState == PlayingService.RunningState.RUNNING) {
                    audioCoachPlayer.playWithMediaPlayer(audioEntity.audioData)
                }
            },
            onFailure = { exception ->
                Log.e("PlayingViewModel", "Failed to play time feedback", exception)
            },
        )
    }

    private fun stopAudioCoaching() {
        audioCoachingJob?.cancel()
        audioCoachPlayer.stopPlaying()
    }

    private fun startServiceAutomatically() = intent {
        sendCommandToService(PlayingService.ACTION_START)
    }

    fun onPauseClicked() = intent {
        sendCommandToService(PlayingService.ACTION_PAUSE)
    }

    fun onResumeClicked() = intent {
        sendCommandToService(PlayingService.ACTION_RESUME)
    }

    fun onStopClicked() = intent {
        sendCommandToService(PlayingService.ACTION_STOP)
    }

    fun cancelStop() = intent {
        // 사용자가 정지를 취소한 경우 - 아무 동작 없음
        Log.d("PlayingViewModel", "Stop cancelled by user")
    }

    fun toggleVolume() = intent {
        reduce {
            state.copy(isVolumeOn = !state.isVolumeOn)
        }

        // 볼륨 끄면 현재 재생 중인 오디오 정지
        if (!state.isVolumeOn) {
            audioCoachPlayer.stopPlaying()
        }
    }

    private fun sendCommandToService(action: String) {
        Intent(context, PlayingService::class.java).apply {
            this.action = action
            context.startForegroundService(this)
        }
    }

    private fun formatTime(millis: Long): String {
        val totalSeconds = millis / 1000
        val minutes = (totalSeconds / 60) % 60
        val seconds = totalSeconds % 60
        val hours = totalSeconds / 3600

        return if (hours > 0) {
            String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        }
    }

    private fun formatPace(paceMinPerKm: Float): String {
        if (paceMinPerKm <= 0 || paceMinPerKm.isInfinite() || paceMinPerKm.isNaN()) {
            return "--'--\""
        }

        val minutes = paceMinPerKm.toInt()
        val seconds = ((paceMinPerKm - minutes) * 60).toInt()
        return String.format(Locale.getDefault(), "%d'%02d\"", minutes, seconds)
    }

    override fun onCleared() {
        super.onCleared()
        audioCoachPlayer.stopPlaying()
        paceUpdateJob?.cancel()
        audioCoachingJob?.cancel()
    }
}

data class PlayingState(
    val runningState: PlayingService.RunningState = PlayingService.RunningState.IDLE,
    val recordId: Int? = null,
    val elapsedTimeMillis: Long = 0L,
    val distanceKm: Float = 0f,
    val avgPaceMinPerKm: Float = 0f,
    val currentSpeedKmh: Float = 0f,
    val isVolumeOn: Boolean = true,
    val recentLocations: List<com.yapp.fitrun.core.domain.entity.LocationEntity> = emptyList(),
    val totalLocationCount: Int = 0,
    // UI용 포맷된 문자열
    val formattedTime: String = "00:00",
    val formattedDistance: String = "0.00",
    val formattedAvgPace: String = "--'--\"",
    // 오디오 코칭
    val preloadedCoachAudio: ByteArray? = null,
    // 목표 관련 필드 추가
    val paceGoal: Long? = null, // 밀리초 단위
    val distanceMeterGoal: Double? = null, // 미터 단위
    val timeGoal: Long? = null, // 밀리초 단위
    val runnerType: String? = null,
) {
    // 경로를 그릴 수 있는지 확인하는 헬퍼 메서드
    fun canDrawPath(): Boolean = recentLocations.size >= 2
}

// Side Effects
sealed class PlayingSideEffect {
    data class RunningStarted(val recordId: Int) : PlayingSideEffect()
    data class NavigateToResult(val recordId: Int) : PlayingSideEffect()
}
