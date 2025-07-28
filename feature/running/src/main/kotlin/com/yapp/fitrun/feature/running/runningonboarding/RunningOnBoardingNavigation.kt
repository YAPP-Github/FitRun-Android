package com.yapp.fitrun.feature.running.runningonboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.yapp.fitrun.core.common.extension.sharedViewModel
import com.yapp.fitrun.feature.running.runningonboarding.viewmodel.RunningOnBoardingViewModel
import kotlinx.serialization.Serializable

@Serializable
data object RunningOnBoardingRoute

@Serializable
data object RunningOnBoardingFirstRoute

@Serializable
data object RunningOnBoardingSecondRoute

@Serializable
data object RunningOnBoardingThirdRoute

fun NavController.navigateToRunningOnBoardingFirst() {
    navigate(RunningOnBoardingFirstRoute)
}

fun NavController.navigateToRunningOnBoardingSecond() {
    navigate(RunningOnBoardingSecondRoute)
}

fun NavController.navigateToRunningOnBoardingThird() {
    navigate(RunningOnBoardingThirdRoute)
}

fun NavGraphBuilder.runningOnBoardingNavGraph(
    navController: NavController,
    onBackClick: () -> Unit,
    onNavigateToReady: () -> Unit,
    onNavigateToRunningOnBoardingSecond: () -> Unit,
    onNavigateToRunningOnBoardingThird: () -> Unit,
) {
    navigation<RunningOnBoardingRoute>(
        startDestination = RunningOnBoardingFirstRoute,
    ) {
        composable<RunningOnBoardingFirstRoute> {
            RunningOnBoardingFirstRoute(
                onBackClick = onBackClick,
                navigateToReady = onNavigateToReady,
                navigateToRunningOnBoardingSecond = onNavigateToRunningOnBoardingSecond,
            )
        }

        composable<RunningOnBoardingSecondRoute> { entry ->
            val viewModel = entry.sharedViewModel<RunningOnBoardingViewModel>(navController)

            RunningOnBoardingSecondRoute(
                onBackClick = onBackClick,
                navigateToReady = onNavigateToReady,
                viewModel = viewModel,
                navigateToRunningOnBoardingThird = onNavigateToRunningOnBoardingThird,
            )
        }

        composable<RunningOnBoardingThirdRoute> { entry ->
            val viewModel = entry.sharedViewModel<RunningOnBoardingViewModel>(navController)
            RunningOnBoardingThirdRoute(
                onBackClick = onBackClick,
                navigateToReady = onNavigateToReady,
                viewModel = viewModel,
            )
        }
    }
}
