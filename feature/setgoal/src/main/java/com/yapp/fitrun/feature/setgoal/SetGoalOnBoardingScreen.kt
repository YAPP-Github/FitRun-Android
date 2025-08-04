package com.yapp.fitrun.feature.setgoal

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.core.ui.FitRunTextButton
import com.yapp.fitrun.core.ui.NavigationTopAppBar
import com.yapp.fitrun.core.ui.advancedImePadding
import com.yapp.fitrun.core.ui.noRippleClickable
import com.yapp.fitrun.feature.setgoal.component.SetPaceOnBoardingSection
import com.yapp.fitrun.feature.setgoal.viewmodel.SetGoalSideEffect
import com.yapp.fitrun.feature.setgoal.viewmodel.SetGoalState
import com.yapp.fitrun.feature.setgoal.viewmodel.SetGoalViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun SetGoalOnBoardingRoute(
    onBackClick: () -> Unit,
    padding: PaddingValues,
    viewModel: SetGoalViewModel = hiltViewModel(),
    onNavigateToComplete: (Int) -> Unit = {},
) {
    val state by viewModel.collectAsState()
    var showSnackBar by remember { mutableStateOf(true) }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SetGoalSideEffect.NavigateToRecordDetail -> onNavigateToComplete(sideEffect.recordId)
            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = padding.calculateBottomPadding()),
    ) {
        SetGoalOnBoardingScreen(
            onBackClick = onBackClick,
            state = state,
            onPaceChange = { value ->
                if (value <= 390) {
                    viewModel.showPaceWarning()
                }
            },
            onSubmit = viewModel::setPaceGoalOnBoarding,
            onSnackBarClick = { showSnackBar = false },
            showSnackBar = showSnackBar,
        )
    }
}

@Composable
internal fun SetGoalOnBoardingScreen(
    onBackClick: () -> Unit,
    state: SetGoalState,
    onPaceChange: (Int) -> Unit,
    onSubmit: () -> Unit,
    onSnackBarClick: () -> Unit,
    showSnackBar: Boolean,
) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("toast_set_goal_completed.json"))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1,
        speed = 1.3f,
        restartOnPlay = false,
        isPlaying = state.setPaceOnBoardingSuccess,
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .focusable(true)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
            .background(colorResource(R.color.bg_primary)),
        topBar = {
            NavigationTopAppBar(
                onLeftNavigationClick = onBackClick,
                leftNavigationIconTint = colorResource(R.color.fg_icon_primary),
                isRightIconVisible = false,
            )
        },
        bottomBar = {
            FitRunTextButton(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 12.dp)
                    .advancedImePadding(),
                onClick = onSubmit,
                text = "설정하기",
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(scrollState),
        ) {
            Box {
                androidx.compose.animation.AnimatedVisibility(
                    visible = state.showPaceWarning,
                    enter = fadeIn() + slideIn { IntOffset(0, it.height) },
                    exit = fadeOut() + slideOut { IntOffset(0, it.height) },
                ) {
                    Image(
                        modifier = Modifier
                            .padding(top = 16.dp, start = 20.dp, end = 20.dp)
                            .height(48.dp)
                            .align(Alignment.TopCenter),
                        painter = painterResource(R.drawable.ic_warning),
                        contentDescription = "warning",
                        contentScale = ContentScale.Fit,
                    )
                }

                if (showSnackBar) {
                    Image(
                        modifier = Modifier
                            .padding(top = 56.dp)
                            .width(209.dp)
                            .height(34.dp)
                            .align(Alignment.TopCenter)
                            .noRippleClickable { onSnackBarClick() },
                        painter = painterResource(R.drawable.ic_snackbar),
                        contentDescription = "set pace goal",
                        contentScale = ContentScale.Fit,
                    )
                }

                SetPaceOnBoardingSection(
                    modifier = Modifier
                        .padding(top = 108.dp),
                    initialPace = state.currentPaceInput ?: 420,
                    onPaceChange = { paceSeconds ->
                        onPaceChange(paceSeconds)
                    },
                )

                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.TopCenter)
                        .padding(top = 142.dp),
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Image(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 36.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(R.drawable.ic_info),
                contentDescription = "set pace goal",
                contentScale = ContentScale.Fit,
            )
        }
    }
}

@Preview
@Composable
fun SetGoalOnBoardingScreenPreview() {
    SetGoalOnBoardingScreen(
        onBackClick = {},
        state = SetGoalState(setPaceOnBoardingSuccess = true),
        onPaceChange = {},
        onSubmit = {},
        onSnackBarClick = {},
        showSnackBar = true,
    )
}
