package com.yapp.fitrun.feature.running.playing

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yapp.fitrun.core.designsystem.Body_body4_regular
import com.yapp.fitrun.core.designsystem.Number_number1_bold
import com.yapp.fitrun.core.designsystem.Number_number3_bold
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.feature.running.playing.viewmodel.PlayingSideEffect
import com.yapp.fitrun.feature.running.playing.viewmodel.PlayingState
import com.yapp.fitrun.feature.running.playing.viewmodel.PlayingViewModel
import com.yapp.fitrun.feature.running.service.PlayingService
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun PlayingRoute(
    padding: PaddingValues,
    viewModel: PlayingViewModel,
    onNavigateToSetGoalOnBoarding: () -> Unit,
) {
    val state by viewModel.collectAsState()
    val displayedPace by viewModel.displayedPace.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is PlayingSideEffect.NavigateToResult -> {
                onNavigateToSetGoalOnBoarding()
            }

            else -> {}
        }
    }

    BackHandler(true) {
        showDialog = !showDialog
    }

    Box(modifier = Modifier.fillMaxSize()) {
        PlayingScreen(
            padding = padding,
            state = state,
            displayedPace = displayedPace,
            onPauseClicked = viewModel::onPauseClicked,
            onResumeClicked = viewModel::onResumeClicked,
            onStopClicked = viewModel::onStopClicked,
            onVolumeToggle = viewModel::toggleVolume,
        )
        if (showDialog) {
            CancelRunningDialog(
                onDismiss = {
                    showDialog = false
                },
                onConfirm = {
                    viewModel.onStopClicked()
                },
            )
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
internal fun PlayingScreen(
    padding: PaddingValues,
    state: PlayingState,
    displayedPace: String = "--'--\"",
    onPauseClicked: () -> Unit = {},
    onResumeClicked: () -> Unit = {},
    onStopClicked: () -> Unit = {},
    onVolumeToggle: () -> Unit = {},
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding(),
            )
            .background(
                colorResource(
                    when (state.runningState) {
                        PlayingService.RunningState.RUNNING -> R.color.fg_nuetral_gray1000
                        else -> R.color.bg_secondary
                    },
                ),
            ),
    ) {
        // 상단
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.TopEnd,
        ) {
            Icon(
                painter = painterResource(
                    id = if (state.isVolumeOn) R.drawable.ic_volume_on else R.drawable.ic_volume_off,
                ),
                contentDescription = "Volume",
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                    ) {
                        onVolumeToggle()
                    }
                    .padding(end = 20.dp, top = 20.dp)
                    .size(32.dp),
                tint = colorResource(
                    when (state.runningState) {
                        PlayingService.RunningState.RUNNING -> R.color.fg_text_interactive_inverse
                        else -> R.color.fg_text_primary
                    },
                ),
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 142.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = state.formattedDistance,
                    style = Number_number1_bold,
                    color = colorResource(
                        when (state.runningState) {
                            PlayingService.RunningState.RUNNING -> R.color.fg_text_interactive_inverse
                            else -> R.color.fg_text_primary
                        },
                    ),
                )
                Text(
                    text = "km",
                    fontSize = 34.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.fg_nuetral_gray700),
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp))
                .background(Color.White),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "평균 페이스",
                            style = Body_body4_regular,
                            color = colorResource(R.color.fg_text_tertiary),
                        )
                        Text(
                            text = displayedPace, // 1초마다 갱신되는 페이스 사용
                            style = Number_number3_bold,
                            color = colorResource(R.color.fg_text_primary),
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(60.dp)
                            .background(Color(0xFFD9D9D9)),
                    )

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "시간",
                            style = Body_body4_regular,
                            color = colorResource(R.color.fg_text_tertiary),
                        )
                        Text(
                            text = state.formattedTime,
                            style = Number_number3_bold,
                            color = colorResource(R.color.fg_text_primary),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(128.dp))

                // 버튼 상태 처리
                when (state.runningState) {
                    PlayingService.RunningState.IDLE -> {
                        // 서비스 시작 중 로딩 표시
                        CircularProgressIndicator(
                            modifier = Modifier.size(110.dp),
                            color = Color(0xFFFF5500),
                            strokeWidth = 4.dp,
                        )
                    }

                    PlayingService.RunningState.RUNNING -> {
                        Button(
                            onClick = {
                                context.vibrate(100)
                                onPauseClicked()
                            },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFF5500),
                            ),
                            modifier = Modifier.size(110.dp),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_pause),
                                contentDescription = "Pause",
                                tint = Color.White,
                                modifier = Modifier.size(40.dp),
                            )
                        }
                    }

                    PlayingService.RunningState.PAUSED -> {
                        Row {
                            // 정지 버튼
                            Button(
                                onClick = {
                                    context.vibrate(100)
                                    onStopClicked()
                                },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(R.color.bg_interactive_secondary_hoverd),
                                ),
                                modifier = Modifier.size(90.dp),
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_stop),
                                    contentDescription = "Stop",
                                    tint = colorResource(R.color.gray_0),
                                    modifier = Modifier.size(32.dp),
                                )
                            }

                            Spacer(modifier = Modifier.width(51.dp))

                            // 재개 버튼
                            Button(
                                onClick = {
                                    context.vibrate(100)
                                    onResumeClicked()
                                },
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(R.color.bg_interactive_primary),
                                ),
                                modifier = Modifier.size(90.dp),
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_play),
                                    contentDescription = "Resume",
                                    tint = colorResource(R.color.gray_0),
                                    modifier = Modifier.size(32.dp),
                                )
                            }
                        }
                    }

                    PlayingService.RunningState.STOPPED -> {
                        // 정지 중 로딩 표시
                        CircularProgressIndicator(
                            modifier = Modifier.size(110.dp),
                            color = Color(0xFFFF5500),
                            strokeWidth = 4.dp,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PlayingScreenRunningPreview() {
    PlayingScreen(
        padding = PaddingValues(),
        state = PlayingState(
            runningState = PlayingService.RunningState.RUNNING,
            formattedDistance = "1.87",
            formattedAvgPace = "5'30\"",
            currentSpeedKmh = 10.5f,
            formattedTime = "15:00",
            isVolumeOn = true,
        ),
        displayedPace = "5'30\"",
    )
}

@Preview(showBackground = true)
@Composable
private fun PlayingScreenPausedPreview() {
    PlayingScreen(
        padding = PaddingValues(),
        state = PlayingState(
            runningState = PlayingService.RunningState.PAUSED,
            formattedDistance = "3.21",
            formattedAvgPace = "6'15\"",
            currentSpeedKmh = 9.6f,
            formattedTime = "20:05",
            isVolumeOn = false,
        ),
        displayedPace = "6'15\"",
    )
}

@Preview(showBackground = true)
@Composable
private fun PlayingScreenIdlePreview() {
    PlayingScreen(
        padding = PaddingValues(),
        state = PlayingState(
            runningState = PlayingService.RunningState.IDLE,
        ),
        displayedPace = "--'--\"",
    )
}

@RequiresPermission(Manifest.permission.VIBRATE)
fun Context.vibrate(duration: Long = 100L) {
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    vibrator.vibrate(
        VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE),
    )
}
