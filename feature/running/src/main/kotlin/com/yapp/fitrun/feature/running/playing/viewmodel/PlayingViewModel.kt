package com.yapp.fitrun.feature.running.playing.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.yapp.fitrun.feature.running.service.PlayingService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.combine
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
) : ViewModel(), ContainerHost<PlayingState, PlayingSideEffect> {

    override val container: Container<PlayingState, PlayingSideEffect> = container(
        PlayingState(
            runningState = PlayingService.RunningState.IDLE, // 초기값을 IDLE로 설정
        ),
    )

    init {
        observeServiceState()
        startServiceAutomatically()
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
                        60f / runningData.currentPaceMinPerKm // 분/km를 km/h로 변환
                    } else 0f,
                    formattedTime = formatTime(runningData.elapsedTimeMillis),
                    formattedDistance = String.format(
                        Locale.getDefault(),
                        "%.2f",
                        runningData.distanceMeters / 1000f,
                    ),
                    formattedAvgPace = formatPace(runningData.avgPaceMinPerKm),
                )
            }
        }
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
        postSideEffect(PlayingSideEffect.ShowStopConfirmDialog)
    }

    fun confirmStop() = intent {
        sendCommandToService(PlayingService.ACTION_STOP)
        postSideEffect(PlayingSideEffect.NavigateToResult)
    }

    fun toggleVolume() = intent {
        reduce {
            state.copy(isVolumeOn = !state.isVolumeOn)
        }
        // TODO: 실제 음성 안내 on/off 구현
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
}

// State
data class PlayingState(
    val runningState: PlayingService.RunningState = PlayingService.RunningState.IDLE,
    val elapsedTimeMillis: Long = 0L,
    val distanceKm: Float = 0f,
    val avgPaceMinPerKm: Float = 0f,
    val currentSpeedKmh: Float = 0f,
    val isVolumeOn: Boolean = true,
    // UI용 포맷된 문자열
    val formattedTime: String = "00:00",
    val formattedDistance: String = "0.00",
    val formattedAvgPace: String = "--'--\"",
)

// Side Effects
sealed class PlayingSideEffect {
    object ShowPauseDialog : PlayingSideEffect()
    object ShowStopConfirmDialog : PlayingSideEffect()
    object NavigateToResult : PlayingSideEffect()
    data class ShowError(val message: String) : PlayingSideEffect()
}
