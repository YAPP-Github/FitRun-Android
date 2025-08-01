package com.yapp.fitrun.feature.running

import androidx.compose.foundation.layout.PaddingValues
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yapp.fitrun.feature.running.finish.FinishRoute
import com.yapp.fitrun.feature.running.playing.PlayingRoute
import com.yapp.fitrun.feature.running.playing.viewmodel.PlayingViewModel
import com.yapp.fitrun.feature.running.ready.ReadyRoute
import kotlinx.serialization.Serializable

@Serializable
data object ReadyRoute

@Serializable
data object PlayingRoute

@Serializable
data object FinishRoute

fun NavController.navigateToReady() {
    navigate(ReadyRoute)
}

fun NavController.navigateToPlaying() {
    navigate(PlayingRoute) {
        // 현재 화면을 백스택에서 제거
        popUpTo(currentBackStackEntry?.destination?.route ?: return@navigate) {
            inclusive = true
        }
    }
}

fun NavGraphBuilder.runningNavGraph(
    padding: PaddingValues,
    onNavigateToPlay: () -> Unit = {},
    onNavigateToSetGoalOnBoarding: () -> Unit,
) {
    composable<ReadyRoute> {
        ReadyRoute(
            onNavigateToPlay = onNavigateToPlay,
        )
    }

    composable<PlayingRoute> {
        val viewModel = hiltViewModel<PlayingViewModel>()

        PlayingRoute(
            padding = padding,
            viewModel = viewModel,
            onNavigateToSetGoalOnBoarding = onNavigateToSetGoalOnBoarding,
        )
    }

    composable<FinishRoute> {
        FinishRoute()
    }
}
