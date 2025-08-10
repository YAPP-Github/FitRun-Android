package com.yapp.fitrun.feature.running.playing.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yapp.fitrun.core.domain.repository.AudioRepository
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
    private val COACH_INTERVAL = 60000L // 1분
    private val RUNNING_INFO_INTERVAL = 300000L // 5분
    private val PACE_FEEDBACK_INTERVAL = 180000L // 3분
    private val TIME_FEEDBACK_INTERVAL = 600000L // 10분

    // 마지막 피드백 시간 추적
    private var lastCoachTime = 0L
    private var lastRunningInfoTime = 0L
    private var lastDistanceFeedback = 0f
    private var lastPaceFeedbackTime = 0L
    private var lastTimeFeedbackTime = 0L

    // 목표 설정 (실제로는 Goal Repository에서 가져와야 함)
    private var goalPace: Float? = null // 분/km
    private var goalDistance: Float? = null // km
    private var goalTime: Int? = null // 분

    init {
        observeServiceState()
        observeRecordId()
        startServiceAutomatically()
        preloadCoachAudio() // 코치 오디오 미리 로드
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
                // 1. 코치 음성 (1분마다)
                if (currentTime - lastCoachTime >= COACH_INTERVAL) {
                    playCoachAudio()
                    lastCoachTime = currentTime
                }

                // 2. 러닝 정보 피드백 (5분마다)
                if (currentTime - lastRunningInfoTime >= RUNNING_INFO_INTERVAL) {
                    playRunningInfo(currentPace)
                    lastRunningInfoTime = currentTime
                }

                // 3. 거리 피드백 (1km마다)
                if (currentDistance - lastDistanceFeedback >= 1f) {
                    playDistanceFeedback(currentDistance)
                    lastDistanceFeedback = currentDistance.toInt().toFloat()
                }

                // 4. 페이스 피드백 (3분마다, 목표 페이스와 비교)
                if (currentTime - lastPaceFeedbackTime >= PACE_FEEDBACK_INTERVAL) {
                    playPaceFeedback(currentPace)
                    lastPaceFeedbackTime = currentTime
                }

                // 5. 시간 피드백 (10분마다)
                if (currentTime - lastTimeFeedbackTime >= TIME_FEEDBACK_INTERVAL) {
                    playTimeFeedback(currentTime)
                    lastTimeFeedbackTime = currentTime
                }
            }
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

    private fun playRunningInfo(currentPace: Float) = intent {
        val paceMillis = (currentPace * 60 * 1000).toInt().toString()

        audioRepository.getRunningInfo(paceMillis).fold(
            onSuccess = { audioEntity ->
                if (state.isVolumeOn && state.runningState == PlayingService.RunningState.RUNNING) {
                    audioCoachPlayer.playWithMediaPlayer(audioEntity.audioData)
                }
            },
            onFailure = {
                // 에러 처리
            },
        )
    }

    private fun playDistanceFeedback(currentDistance: Float) = intent {
        val feedbackType = when {
            goalDistance != null && currentDistance >= goalDistance!! -> "DISTANCE_LEFT_1KM"
            currentDistance.toInt() % 5 == 0 -> "MILESTONE" // 5km마다 특별 피드백
            else -> "NORMAL"
        }

        audioRepository.getDistanceFeedback(feedbackType).fold(
            onSuccess = { audioEntity ->
                if (state.isVolumeOn && state.runningState == PlayingService.RunningState.RUNNING) {
                    audioCoachPlayer.playWithMediaPlayer(audioEntity.audioData)
                }
            },
            onFailure = {
                // 에러 처리
            },
        )
    }

    private fun playPaceFeedback(currentPace: Float) = intent {
        val feedbackType = when {
            goalPace == null -> "NORMAL"
            currentPace < goalPace!! * 0.9f -> "TOO_FAST"
            currentPace > goalPace!! * 1.1f -> "TOO_SLOW"
            else -> "ON_PACE"
        }

        audioRepository.getPaceFeedback(feedbackType).fold(
            onSuccess = { audioEntity ->
                if (state.isVolumeOn && state.runningState == PlayingService.RunningState.RUNNING) {
                    audioCoachPlayer.playWithMediaPlayer(audioEntity.audioData)
                }
            },
            onFailure = {
                // 에러 처리
            },
        )
    }

    private fun playTimeFeedback(elapsedTime: Long) = intent {
        val elapsedMinutes = (elapsedTime / 1000 / 60).toInt()
        val feedbackType = when {
            goalTime != null && elapsedMinutes >= goalTime!! -> "GOAL_ACHIEVED"
            elapsedMinutes % 30 == 0 -> "MILESTONE" // 30분마다 특별 피드백
            else -> "NORMAL"
        }

        audioRepository.getTimeFeedback(feedbackType).fold(
            onSuccess = { audioEntity ->
                if (state.isVolumeOn && state.runningState == PlayingService.RunningState.RUNNING) {
                    audioCoachPlayer.playWithMediaPlayer(audioEntity.audioData)
                }
            },
            onFailure = {
                // 에러 처리
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

// State
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
) {
    // 경로를 그릴 수 있는지 확인하는 헬퍼 메서드
    fun canDrawPath(): Boolean = recentLocations.size >= 2
}

// Side Effects
sealed class PlayingSideEffect {
    data class RunningStarted(val recordId: Int) : PlayingSideEffect()
    data class NavigateToResult(val recordId: Int) : PlayingSideEffect()
}