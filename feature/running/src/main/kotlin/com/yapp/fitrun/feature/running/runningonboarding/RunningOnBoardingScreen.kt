package com.yapp.fitrun.feature.running.runningonboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.fitrun.core.designsystem.Body_body2_bold
import com.yapp.fitrun.core.designsystem.Body_body3_medium
import com.yapp.fitrun.core.designsystem.Body_body3_semiBold
import com.yapp.fitrun.core.designsystem.Body_body4_bold
import com.yapp.fitrun.core.designsystem.Body_body4_regular
import com.yapp.fitrun.core.designsystem.Head_h1_bold
import com.yapp.fitrun.feature.running.runningonboarding.viewmodel.RunningOnBoardingState
import com.yapp.fitrun.feature.running.runningonboarding.viewmodel.RunningOnBoardingViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.core.ui.FitRunTextButton
import com.yapp.fitrun.core.ui.FitRunTextIconButton
import com.yapp.fitrun.core.ui.NavigationTopAppBar

@Composable
internal fun RunningOnBoardingRoute(
    onBackClick: () -> Unit,
    navigateToReady: () -> Unit,
    navigateToSetGoals: () -> Unit,
    viewModel: RunningOnBoardingViewModel,
) {
    val uiState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            else -> {}
        }
    }


    RunningOnBoardingFirstScreen(
        uiState = uiState,
        navigateToReady = navigateToReady,
        onBackClick = onBackClick,
        navigateToSetGoals = navigateToSetGoals,
    )
}

@Composable
internal fun RunningOnBoardingFirstScreen(
    uiState: RunningOnBoardingState,
    onBackClick: () -> Unit,
    navigateToReady: () -> Unit,
    navigateToSetGoals: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.fg_nuetral_gray1000)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NavigationTopAppBar(
            onLeftNavigationClick = onBackClick,
            leftNavigationIconTint = colorResource(R.color.fg_icon_interactive_inverse),
            onRightNavigationClick = navigateToReady
        )

        Text(
            text = "반가워요",
            modifier = Modifier.padding(top = 196.dp),
            textAlign = TextAlign.Center,
            color = colorResource(R.color.fg_nuetral_gray0),
            style = Head_h1_bold,
        )

        Spacer(modifier = Modifier.weight(1f))

        FitRunTextButton(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp),
            onClick = navigateToSetGoals,
            text = stringResource(R.string.running_on_boarding_set_goal)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.running_on_boarding_next_time),
            textAlign = TextAlign.Center,
            color = colorResource(R.color.fg_nuetral_gray600),
            style = Body_body3_semiBold,
            modifier = Modifier.clickable { navigateToReady() }.padding(bottom = 49.dp)
        )
    }
}

@Composable
internal fun RunningOnBoardingSecondScreen(
    uiState: RunningOnBoardingState,
    onBackClick: () -> Unit,
    navigateToReady: () -> Unit,
    navigateToSetGoal: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.fg_nuetral_gray1000)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NavigationTopAppBar(
            onLeftNavigationClick = onBackClick,
            onRightNavigationClick = navigateToReady,
            leftNavigationIconTint = colorResource(R.color.fg_icon_interactive_inverse),
            rightIconText = stringResource(R.string.running_on_boarding_skip),
            rightIconColor = colorResource(R.color.fg_text_interactive_secondary),
            isRightIconVisible = true
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
                .padding(top = 44.dp, start = 20.dp, end = 20.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            GoalButton(
                onClick = { navigateToSetGoal(0) },
                imageResource = painterResource(R.drawable.ic_running_clock),
                title = stringResource(R.string.running_on_boarding_goal_time),
                description = stringResource(R.string.running_on_boarding_goal_time_content)
            )

            GoalButton(
                onClick = { navigateToSetGoal(1) },
                modifier = Modifier.padding(start = 12.dp),
                imageResource = painterResource(R.drawable.ic_running_track),
                title = stringResource(R.string.running_on_boarding_goal_distance),
                description = stringResource(R.string.running_on_boarding_goal_distance_content)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        FitRunTextIconButton(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 40.dp),
            text = stringResource(R.string.running_on_boarding_info),
            textColor = colorResource(R.color.fg_text_secondary),
            textStyle = Body_body4_bold,
            imageResource = painterResource(R.drawable.ic_questionmark),
            buttonColor = colorResource(R.color.fg_nuetral_gray200)
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
            .height(200.dp)
            .widthIn(0.dp, 160.dp),
        shape = RoundedCornerShape(dimensionResource(R.dimen.border_radius_500)),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.fg_nuetral_gray900)
        ),
    ) {
        Column(
            modifier = Modifier.wrapContentSize().align(Alignment.CenterVertically),
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

@Preview
@Composable
fun RunningOnBoardingFirstScreenPreview() {
    RunningOnBoardingFirstScreen(
        uiState = RunningOnBoardingState(),
        navigateToReady = {},
        onBackClick = {},
        navigateToSetGoals = {},
    )
}

@Preview
@Composable
fun RunningOnBoardingSecondScreenPreview() {
    RunningOnBoardingSecondScreen(
        uiState = RunningOnBoardingState(),
        navigateToReady = {},
        onBackClick = {},
        navigateToSetGoal = {},
    )
}
