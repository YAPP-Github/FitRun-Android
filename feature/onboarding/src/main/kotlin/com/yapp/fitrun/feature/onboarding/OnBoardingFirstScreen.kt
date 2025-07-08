package com.yapp.fitrun.feature.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.yapp.fitrun.core.designsystem.Head_head1_Bold
import com.yapp.fitrun.core.designsystem.Head_head2_semiBold
import com.yapp.fitrun.core.designsystem.TextPrimary
import com.yapp.fitrun.core.ui.OnBoardingTopAppBar
import com.yapp.fitrun.feature.onboarding.viewmodel.OnBoardingSideEffect
import com.yapp.fitrun.feature.onboarding.viewmodel.OnBoardingViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import com.yapp.fitrun.core.designsystem.R

@Composable
internal fun OnBoardingFirstRoute(
    navigateToHome: () -> Unit,
    navigateToRoutine: () -> Unit,
    viewModel: OnBoardingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            OnBoardingSideEffect.NavigateToHome -> navigateToHome()
            OnBoardingSideEffect.NavigateToRoutine -> navigateToRoutine()
        }
    }
}

@Composable
internal fun OnBoardingFirstScreen() {
    Column {
        OnBoardingTopAppBar(
            onLeftNavigationClick = {},
            onRightNavigationClick = {}
        )
        Box(
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(top = 24.dp, start = 20.dp, end = 20.dp, bottom = 105.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.on_boarding_first_title),
                    textAlign = TextAlign.Center,
                    color = TextPrimary,
                    style = Body_body1_semiBold,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.on_boarding_first_question1),
                    textAlign = TextAlign.Center,
                    color = TextPrimary,
                    style = Head_head2_semiBold,
                )
            }
        }
    }
}

@Preview
@Composable
fun OnBoardingFirstPreview() {
    OnBoardingFirstScreen(
    )
}
