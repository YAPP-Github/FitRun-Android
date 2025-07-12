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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.fitrun.core.designsystem.Body_body1_semiBold
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.core.designsystem.TextPrimary
import com.yapp.fitrun.core.ui.OnBoardingQuestionGroup
import com.yapp.fitrun.core.ui.OnBoardingTopAppBar
import com.yapp.fitrun.feature.onboarding.viewmodel.OnBoardingSideEffect
import com.yapp.fitrun.feature.onboarding.viewmodel.OnBoardingViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun OnBoardingFourthRoute(
    onBackClick: () -> Unit,
    onNavigateToOnBoardingResult: () -> Unit,
    viewModel: OnBoardingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            OnBoardingSideEffect.NavigateToOnBoardingResult -> onNavigateToOnBoardingResult()
            else -> {}
        }
    }
    OnBoardingFourthScreen(
        onBackClick = onBackClick,
        onNavigateToOnBoardingResult = viewModel::onClickOnBoardingFourth,
    )

}

@Composable
internal fun OnBoardingFourthScreen(
    onBackClick: () -> Unit,
    onNavigateToOnBoardingResult: (Int) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        OnBoardingTopAppBar(
            onLeftNavigationClick = onBackClick,
            onRightNavigationClick = {},
            progress = 1f
        )
        Box(
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp, start = 20.dp, end = 20.dp, bottom = 105.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.on_boarding_fourth_title),
                    textAlign = TextAlign.Center,
                    color = TextPrimary,
                    style = Body_body1_semiBold,
                )

                Spacer(modifier = Modifier.height(16.dp))

                OnBoardingQuestionGroup(
                    questionTitle = stringResource(R.string.on_boarding_fourth_question1),
                    questionOptions = listOf(
                        stringResource(R.string.on_boarding_fourth_question1_option1),
                        stringResource(R.string.on_boarding_fourth_question1_option2),
                        stringResource(R.string.on_boarding_fourth_question1_option3),
                        stringResource(R.string.on_boarding_fourth_question1_option4),
                    ),
                    onClick = onNavigateToOnBoardingResult,
                )
            }
        }
    }
}

@Preview
@Composable
fun OnBoardingFourthPreview() {
    OnBoardingFourthScreen(
        onBackClick = {},
        onNavigateToOnBoardingResult = {}
    )
}
