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
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.core.ui.OnBoardingQuestionGroup
import com.yapp.fitrun.core.ui.OnBoardingTopAppBar
import com.yapp.fitrun.feature.onboarding.viewmodel.OnBoardingSideEffect
import com.yapp.fitrun.feature.onboarding.viewmodel.OnBoardingState
import com.yapp.fitrun.feature.onboarding.viewmodel.OnBoardingViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun OnBoardingThirdRoute(
    onBackClick: () -> Unit,
    onNavigateToOnBoardingFourth: () -> Unit,
    viewModel: OnBoardingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            OnBoardingSideEffect.NavigateToOnBoardingFourth -> onNavigateToOnBoardingFourth()
            else -> {}
        }
    }

    OnBoardingThirdScreen(
        uiState = uiState,
        onClickOnBoardingThirdQuestion1 = viewModel::onClickOnBoardingThirdQuestion1,
        onClickOnBoardingThirdQuestion2 = viewModel::onClickOnBoardingThirdQuestion2,
        onBackClick = onBackClick,
    )
}

@Composable
internal fun OnBoardingThirdScreen(
    uiState: OnBoardingState,
    onClickOnBoardingThirdQuestion1: (Int) -> Unit,
    onClickOnBoardingThirdQuestion2: (Int) -> Unit,
    onBackClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState),
    ) {
        OnBoardingTopAppBar(
            onLeftNavigationClick = onBackClick,
            onRightNavigationClick = {},
            progress = 0.75f,
        )
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp, start = 20.dp, end = 20.dp, bottom = 105.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.on_boarding_third_title),
                    textAlign = TextAlign.Center,
                    color = colorResource(R.color.fg_nuetral_gray700),
                    style = Body_body1_semiBold,
                )

                Spacer(modifier = Modifier.height(16.dp))

                OnBoardingQuestionGroup(
                    questionTitle = stringResource(R.string.on_boarding_third_question1),
                    questionOptions = listOf(
                        stringResource(R.string.on_boarding_third_question1_option1),
                        stringResource(R.string.on_boarding_third_question1_option2),
                        stringResource(R.string.on_boarding_third_question1_option3),
                    ),
                    onClick = onClickOnBoardingThirdQuestion1,
                    visible = uiState.isSelectedOnBoardingThirdQ2State,
                )

                OnBoardingQuestionGroup(
                    questionTitle = stringResource(R.string.on_boarding_third_question2),
                    questionOptions = listOf(
                        stringResource(R.string.on_boarding_third_question2_option1),
                        stringResource(R.string.on_boarding_third_question2_option2),
                        stringResource(R.string.on_boarding_third_question2_option3),
                    ),
                    onClick = onClickOnBoardingThirdQuestion2,
                )
            }
        }
    }
}

@Preview
@Composable
fun OnBoardingThirdPreview() {
    OnBoardingThirdScreen(
        uiState = OnBoardingState(),
        onBackClick = {},
        onClickOnBoardingThirdQuestion1 = {},
        onClickOnBoardingThirdQuestion2 = {},
    )
}
