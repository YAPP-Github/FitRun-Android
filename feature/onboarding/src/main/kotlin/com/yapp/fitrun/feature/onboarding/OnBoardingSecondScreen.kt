package com.yapp.fitrun.feature.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.yapp.fitrun.core.ui.OnBoardingTopAppBar
import com.yapp.fitrun.feature.onboarding.viewmodel.OnBoardingSideEffect
import com.yapp.fitrun.feature.onboarding.viewmodel.OnBoardingViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
internal fun OnBoardingSecondRoute(
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
internal fun OnBoardingSecondScreen() {
    Column {
        OnBoardingTopAppBar(
            onLeftNavigationClick = {},
            onRightNavigationClick = {}
        )
    }

}

@Preview
@Composable
fun OnBoardingSecondPreview() {
    OnBoardingSecondScreen(
    )
}
