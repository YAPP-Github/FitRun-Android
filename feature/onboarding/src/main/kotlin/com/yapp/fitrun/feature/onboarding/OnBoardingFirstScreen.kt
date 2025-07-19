package com.yapp.fitrun.feature.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.yapp.fitrun.core.ui.OnBoardingTopAppBar
import com.yapp.fitrun.core.ui.OnBoardingQuestionGroup
import com.yapp.fitrun.feature.onboarding.viewmodel.OnBoardingViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.feature.onboarding.viewmodel.OnBoardingSideEffect
import com.yapp.fitrun.feature.onboarding.viewmodel.OnBoardingState

@Composable
internal fun OnBoardingFirstRoute(
    onNavigateToOnBoardingSecond: () -> Unit,
    viewModel: OnBoardingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            OnBoardingSideEffect.NavigateToOnBoardingSecond -> onNavigateToOnBoardingSecond()
            else -> {}
        }
    }

    OnBoardingFirstScreen(
        uiState = uiState,
        onClickOnBoardingFirstQuestion1 = viewModel::onClickOnBoardingFirstQuestion1,
        onClickOnBoardingFirstQuestion2 = viewModel::onClickOnBoardingFirstQuestion2,
        onClickOnBoardingFirstQuestion3 = viewModel::onClickOnBoardingFirstQuestion3,
        onClickOnBoardingFirstQuestion4 = viewModel::onClickOnBoardingFirstQuestion4,
    )
}

@Composable
internal fun OnBoardingFirstScreen(
    uiState: OnBoardingState,
    onClickOnBoardingFirstQuestion1: (Int) -> Unit,
    onClickOnBoardingFirstQuestion2: (Int) -> Unit,
    onClickOnBoardingFirstQuestion3: (Int) -> Unit,
    onClickOnBoardingFirstQuestion4: (Int) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        OnBoardingTopAppBar(
            onLeftNavigationClick = {},
            onRightNavigationClick = {},
            progress = 0.25f
        )
        Box(
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp, start = 20.dp, end = 20.dp, bottom = 45.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.on_boarding_first_title),
                    textAlign = TextAlign.Center,
                    color = colorResource(R.color.fg_nuetral_gray700),
                    style = Body_body1_semiBold,
                )

                Spacer(modifier = Modifier.height(16.dp))

                OnBoardingQuestionGroup(
                    questionTitle = stringResource(R.string.on_boarding_first_question1),
                    questionOptions = listOf(
                        stringResource(R.string.on_boarding_first_question1_option1),
                        stringResource(R.string.on_boarding_first_question1_option2),
                        stringResource(R.string.on_boarding_first_question1_option3),
                    ),
                    onClick = onClickOnBoardingFirstQuestion1,
                    visible = (uiState.selectedOnBoardingFirstStateCount >= 3),
                )

                OnBoardingQuestionGroup(
                    questionTitle = stringResource(R.string.on_boarding_first_question2),
                    questionOptions = listOf(
                        stringResource(R.string.on_boarding_first_question2_option1),
                        stringResource(R.string.on_boarding_first_question2_option2),
                        stringResource(R.string.on_boarding_first_question2_option3),
                    ),
                    onClick =  onClickOnBoardingFirstQuestion2,
                    visible = (uiState.selectedOnBoardingFirstStateCount >= 2),
                )

                OnBoardingQuestionGroup(
                    questionTitle = stringResource(R.string.on_boarding_first_question3),
                    questionOptions =
                        listOf(
                            stringResource(R.string.on_boarding_first_question3_option1),
                            stringResource(R.string.on_boarding_first_question3_option2),
                            stringResource(R.string.on_boarding_first_question3_option3),
                        ),
                    onClick = onClickOnBoardingFirstQuestion3,
                    visible = (uiState.selectedOnBoardingFirstStateCount >= 1),
                )

                OnBoardingQuestionGroup(
                    questionTitle = stringResource(R.string.on_boarding_first_question4),
                    questionOptions =
                        listOf(
                            stringResource(R.string.on_boarding_first_question4_option1),
                            stringResource(R.string.on_boarding_first_question4_option2),
                            stringResource(R.string.on_boarding_first_question4_option3),
                        ),
                    onClick = onClickOnBoardingFirstQuestion4,
                )
            }
        }
    }
}

@Preview
@Composable
fun OnBoardingFirstPreview() {
    OnBoardingFirstScreen(
        OnBoardingState(),
        onClickOnBoardingFirstQuestion1 = {},
        onClickOnBoardingFirstQuestion2 = {},
        onClickOnBoardingFirstQuestion3 = {},
        onClickOnBoardingFirstQuestion4 = {},
    )
}
