package com.yapp.fitrun.feature.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.fitrun.core.designsystem.Body_body1_semiBold
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.core.ui.OnBoardingQuestionGroup
import com.yapp.fitrun.core.ui.OnBoardingTopAppBar
import com.yapp.fitrun.feature.onboarding.viewmodel.OnBoardingSideEffect
import com.yapp.fitrun.feature.onboarding.viewmodel.OnBoardingState
import com.yapp.fitrun.feature.onboarding.viewmodel.OnBoardingViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun OnBoardingSecondRoute(
    padding: PaddingValues,
    onBackClick: () -> Unit,
    onNavigateToOnBoardingThird: () -> Unit,
    viewModel: OnBoardingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            OnBoardingSideEffect.NavigateToOnBoardingThird -> onNavigateToOnBoardingThird()
            else -> {}
        }
    }

    OnBoardingSecondScreen(
        padding = padding,
        uiState = uiState,
        onClickOnBoardingSecondQuestion1 = viewModel::onClickOnBoardingSecondQuestion1,
        onClickOnBoardingSecondQuestion2 = viewModel::onClickOnBoardingSecondQuestion2,
        onBackClick = onBackClick,
    )
}

@Composable
internal fun OnBoardingSecondScreen(
    padding: PaddingValues,
    uiState: OnBoardingState,
    onClickOnBoardingSecondQuestion1: (Int) -> Unit,
    onClickOnBoardingSecondQuestion2: (Int) -> Unit,
    onBackClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = padding.calculateTopPadding(),
                bottom = padding.calculateBottomPadding(),
            )
            .background(Color.White)
            .verticalScroll(scrollState),
    ) {
        OnBoardingTopAppBar(
            onLeftNavigationClick = onBackClick,
            onRightNavigationClick = {},
            progress = 0.5f,
        )
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp, start = 20.dp, end = 20.dp, bottom = 105.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.on_boarding_second_title),
                    textAlign = TextAlign.Center,
                    color = colorResource(R.color.fg_nuetral_gray700),
                    style = Body_body1_semiBold,
                )

                Spacer(modifier = Modifier.height(16.dp))

                OnBoardingQuestionGroup(
                    questionTitle = stringResource(R.string.on_boarding_second_question1),
                    questionOptions = listOf(
                        stringResource(R.string.on_boarding_second_question1_option1),
                        stringResource(R.string.on_boarding_second_question1_option2),
                        stringResource(R.string.on_boarding_second_question1_option3),
                    ),
                    onClick = onClickOnBoardingSecondQuestion1,
                    visible = uiState.isSelectedOnBoardingSecondQ2State,
                )

                OnBoardingQuestionGroup(
                    questionTitle = stringResource(R.string.on_boarding_second_question2),
                    questionOptions = listOf(
                        stringResource(R.string.on_boarding_second_question2_option1),
                        stringResource(R.string.on_boarding_second_question2_option2),
                        stringResource(R.string.on_boarding_second_question2_option3),
                    ),
                    onClick = onClickOnBoardingSecondQuestion2,
                )
            }
        }
    }
}

@Preview
@Composable
fun OnBoardingSecondPreview() {
    OnBoardingSecondScreen(
        padding = PaddingValues(),
        uiState = OnBoardingState(),
        onBackClick = {},
        onClickOnBoardingSecondQuestion1 = {},
        onClickOnBoardingSecondQuestion2 = {},
    )
}
