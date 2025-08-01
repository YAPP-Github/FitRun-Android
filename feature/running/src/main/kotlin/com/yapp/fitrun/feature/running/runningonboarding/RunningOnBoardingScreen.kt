package com.yapp.fitrun.feature.running.runningonboarding

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.yapp.fitrun.core.designsystem.Body_body2_bold
import com.yapp.fitrun.core.designsystem.Body_body3_medium
import com.yapp.fitrun.core.designsystem.Body_body3_regular
import com.yapp.fitrun.core.designsystem.Body_body3_semiBold
import com.yapp.fitrun.core.designsystem.Body_body4_bold
import com.yapp.fitrun.core.designsystem.Body_body4_regular
import com.yapp.fitrun.core.designsystem.Head_h1_bold
import com.yapp.fitrun.core.designsystem.Head_h2_bold
import com.yapp.fitrun.feature.running.runningonboarding.viewmodel.RunningOnBoardingState
import com.yapp.fitrun.feature.running.runningonboarding.viewmodel.RunningOnBoardingViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.core.designsystem.pretendardFamily
import com.yapp.fitrun.core.ui.FitRunTextButton
import com.yapp.fitrun.core.ui.FitRunTextIconButton
import com.yapp.fitrun.core.ui.NavigationTopAppBar
import com.yapp.fitrun.core.ui.advancedImePadding
import com.yapp.fitrun.core.ui.noRippleClickable
import com.yapp.fitrun.feature.running.runningonboarding.viewmodel.RunningOnBoardingSideEffect

@Composable
internal fun RunningOnBoardingFirstRoute(
    padding: PaddingValues,
    onBackClick: () -> Unit,
    navigateToReady: () -> Unit,
    navigateToRunningOnBoardingSecond: () -> Unit,
) {
    RunningOnBoardingFirstScreen(
        padding = padding,
        navigateToReady = navigateToReady,
        onBackClick = onBackClick,
        navigateToRunningOnBoardingSecond = navigateToRunningOnBoardingSecond,
    )
}

@Composable
internal fun RunningOnBoardingFirstScreen(
    padding: PaddingValues,
    onBackClick: () -> Unit,
    navigateToReady: () -> Unit,
    navigateToRunningOnBoardingSecond: () -> Unit,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("running_onboarding_txt.json"))

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1,
        speed = 1.3f,
        restartOnPlay = false,
        isPlaying = true,
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.fg_nuetral_gray1000)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NavigationTopAppBar(
            onLeftNavigationClick = onBackClick,
            leftNavigationIconTint = colorResource(R.color.fg_icon_interactive_inverse),
            onRightNavigationClick = navigateToReady,
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally),
        ) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center),
            )

            androidx.compose.animation.AnimatedVisibility(
                visible = (progress == 1f),
                enter = fadeIn(
                    initialAlpha = 0f,
                    animationSpec = tween(
                        durationMillis = 100,
                        easing = FastOutSlowInEasing,
                    ),
                ),
            ) {
                Column(
                    modifier = Modifier.wrapContentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    FitRunTextButton(
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                        onClick = navigateToRunningOnBoardingSecond,
                        text = stringResource(R.string.running_on_boarding_set_goal),
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = stringResource(R.string.running_on_boarding_next_time),
                        textAlign = TextAlign.Center,
                        color = colorResource(R.color.fg_nuetral_gray600),
                        style = Body_body3_semiBold,
                        modifier = Modifier.clickable { navigateToReady() },
                    )

                    Spacer(
                        modifier = Modifier
                            .padding(bottom = padding.calculateBottomPadding())
                            .height(28.dp),
                    )
                }
            }
        }
    }
}

@Composable
internal fun RunningOnBoardingSecondRoute(
    padding: PaddingValues,
    onBackClick: () -> Unit,
    navigateToReady: () -> Unit,
    navigateToRunningOnBoardingThird: () -> Unit,
    viewModel: RunningOnBoardingViewModel = hiltViewModel(),
) {
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is RunningOnBoardingSideEffect.NavigateToRunningOnBoardingThird -> navigateToRunningOnBoardingThird()
            else -> {}
        }
    }

    RunningOnBoardingSecondScreen(
        padding = padding,
        navigateToReady = navigateToReady,
        onBackClick = onBackClick,
        onClickSetGoal = viewModel::onClickSetGoal,
    )
}

@Composable
internal fun RunningOnBoardingSecondScreen(
    padding: PaddingValues,
    onBackClick: () -> Unit,
    navigateToReady: () -> Unit,
    onClickSetGoal: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.fg_nuetral_gray1000)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NavigationTopAppBar(
            onLeftNavigationClick = onBackClick,
            onRightNavigationClick = navigateToReady,
            leftNavigationIconTint = colorResource(R.color.fg_icon_interactive_inverse),
            rightIconText = stringResource(R.string.running_on_boarding_skip),
            rightIconColor = colorResource(R.color.fg_text_interactive_secondary),
            isRightIconVisible = true,
        )

        Text(
            text = stringResource(R.string.running_on_boarding_title),
            modifier = Modifier.padding(top = 80.dp),
            textAlign = TextAlign.Center,
            color = colorResource(R.color.fg_nuetral_gray0),
            style = Head_h1_bold,
        )

        Text(
            text = stringResource(R.string.running_on_boarding_description),
            modifier = Modifier.padding(top = 8.dp),
            textAlign = TextAlign.Center,
            color = colorResource(R.color.fg_nuetral_gray0),
            style = Body_body3_medium,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 44.dp, start = 20.dp, end = 20.dp)
                .height(200.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            GoalButton(
                modifier = Modifier.weight(0.5f),
                onClick = { onClickSetGoal(0) },
                imageResource = painterResource(R.drawable.ic_running_clock),
                title = stringResource(R.string.running_on_boarding_goal_time),
                description = stringResource(R.string.running_on_boarding_goal_time_content),
            )

            Spacer(modifier = Modifier.width(12.dp))

            GoalButton(
                modifier = Modifier.weight(0.5f),
                onClick = { onClickSetGoal(1) },
                imageResource = painterResource(R.drawable.ic_running_track),
                title = stringResource(R.string.running_on_boarding_goal_distance),
                description = stringResource(R.string.running_on_boarding_goal_distance_content),
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        FitRunTextIconButton(
            modifier = Modifier
                .padding(bottom = padding.calculateBottomPadding())
                .padding(start = 20.dp, end = 20.dp, bottom = 40.dp),
            text = stringResource(R.string.running_on_boarding_info),
            textColor = colorResource(R.color.fg_text_secondary),
            textStyle = Body_body4_bold,
            imageResource = painterResource(R.drawable.ic_questionmark),
            buttonColor = colorResource(R.color.fg_nuetral_gray200),
        )
    }
}

@Composable
fun GoalButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageResource: Painter,
    title: String,
    description: String,
) {
    Button(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(R.dimen.border_radius_500)),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.fg_nuetral_gray900),
        ),
        contentPadding = PaddingValues(0.dp),
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .width(45.dp)
                    .height(47.dp)
                    .align(Alignment.CenterHorizontally),
                painter = imageResource,
                contentDescription = "set time goal",
                contentScale = ContentScale.Fit,
            )

            Text(
                text = title,
                modifier = Modifier.padding(top = 19.dp),
                textAlign = TextAlign.Center,
                color = colorResource(R.color.fg_text_interactive_inverse),
                style = Body_body2_bold,
            )

            Text(
                text = description,
                modifier = Modifier.padding(top = 4.dp),
                textAlign = TextAlign.Center,
                color = colorResource(R.color.fg_text_interactive_inverse),
                style = Body_body4_regular,
            )
        }
    }
}

@Composable
internal fun RunningOnBoardingThirdRoute(
    padding: PaddingValues,
    onBackClick: () -> Unit,
    navigateToReady: () -> Unit,
    viewModel: RunningOnBoardingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is RunningOnBoardingSideEffect.NavigateToReady -> navigateToReady()
            else -> {}
        }
    }

    RunningOnBoardingThirdScreen(
        padding = padding,
        uiState = uiState,
        onBackClick = onBackClick,
        navigateToReady = navigateToReady,
        onClickSaveGoal = viewModel::onClickSaveGoal,
    )
}

@Composable
internal fun RunningOnBoardingThirdScreen(
    padding: PaddingValues,
    uiState: RunningOnBoardingState,
    onBackClick: () -> Unit,
    navigateToReady: () -> Unit,
    onClickSaveGoal: () -> Unit,
) {
    var isFocused by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val goalValue = remember { mutableStateOf(if (uiState.isShowTimeGoal) "30" else "3") }
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("toast_set_goal_completed.json"))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1,
        speed = 1.0f,
        restartOnPlay = false,
        isPlaying = uiState.isSetGoalSuccess,
    )

    Scaffold(
        modifier = Modifier
            .background(colorResource(R.color.fg_nuetral_gray1000))
            .fillMaxSize()
            .focusable(true)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        topBar = {
            NavigationTopAppBar(
                onLeftNavigationClick = onBackClick,
                leftNavigationIconTint = colorResource(R.color.fg_icon_interactive_inverse),
                isRightIconVisible = false,
            )
        },
        bottomBar = {
            FitRunTextButton(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 12.dp)
                    .padding(bottom = padding.calculateBottomPadding())
                    .advancedImePadding(),
                onClick = onClickSaveGoal,
                text = stringResource(R.string.running_on_boarding_set_goal_run),
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.fg_nuetral_gray1000)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = if (uiState.isShowTimeGoal) stringResource(R.string.running_on_boarding_goal_time_title)
                        else stringResource(R.string.running_on_boarding_goal_distance_title),
                        modifier = Modifier.padding(top = 36.dp),
                        textAlign = TextAlign.Center,
                        color = colorResource(R.color.fg_text_interactive_inverse),
                        style = Head_h2_bold,
                    )

                    Text(
                        text = if (uiState.isShowTimeGoal) stringResource(R.string.running_on_boarding_goal_time_description)
                        else stringResource(R.string.running_on_boarding_goal_distance_description),
                        modifier = Modifier.padding(top = 12.dp),
                        textAlign = TextAlign.Center,
                        color = colorResource(R.color.fg_nuetral_gray400),
                        style = Body_body3_regular,
                    )

                    Row(
                        modifier = Modifier.wrapContentSize(),
                    ) {
                        BasicTextField(
                            modifier = Modifier
                                .width(IntrinsicSize.Min)
                                .onFocusChanged { focused ->
                                    isFocused = focused.isFocused
                                },
                            value = goalValue.value,
                            onValueChange = {
                                goalValue.value = it
                            },
                            textStyle = TextStyle(
                                textAlign = TextAlign.End,
                                fontFamily = pretendardFamily,
                                fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_bold)),
                                fontSize = 52.sp,
                                color = colorResource(R.color.fg_text_interactive_inverse),
                                lineHeight = dimensionResource(id = R.dimen.line_height_1700).value.sp,
                                letterSpacing = dimensionResource(id = R.dimen.number_letter_spacing).value.sp,
                                lineHeightStyle = LineHeightStyle(
                                    alignment = LineHeightStyle.Alignment.Center,
                                    trim = LineHeightStyle.Trim.None,
                                ),
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                            ),
                            decorationBox = { innerTextField ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(all = 16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    innerTextField()
                                    Spacer(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(2.dp)
                                            .background(if (isFocused) colorResource(R.color.bg_interactive_primary) else Color.Unspecified),
                                    )
                                }
                            },
                        )

                        Text(
                            text = if (uiState.isShowTimeGoal) "분" else "km",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(top = 10.dp),
                            textAlign = TextAlign.Center,
                            color = colorResource(R.color.fg_text_disabled),
                            style = Head_h1_bold,
                        )
                    }
                }

                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.TopCenter)
                        .padding(top = 192.dp),
                )
            }
        }
    }
}

@Preview
@Composable
fun RunningOnBoardingFirstScreenPreview() {
    RunningOnBoardingFirstScreen(
        padding = PaddingValues(),
        navigateToReady = {},
        onBackClick = {},
        navigateToRunningOnBoardingSecond = {},
    )
}

@Preview
@Composable
fun RunningOnBoardingSecondScreenPreview() {
    RunningOnBoardingSecondScreen(
        padding = PaddingValues(),
        navigateToReady = {},
        onBackClick = {},
        onClickSetGoal = {},
    )
}

@Preview
@Composable
fun RunningOnBoardingThirdScreenPreview() {
    RunningOnBoardingThirdScreen(
        padding = PaddingValues(),
        uiState = RunningOnBoardingState(isSetGoalSuccess = true),
        navigateToReady = {},
        onBackClick = {},
        onClickSaveGoal = {},
    )
}
