package com.yapp.fitrun.feature.onboarding.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.yapp.fitrun.core.common.extension.sharedViewModel
import com.yapp.fitrun.feature.onboarding.OnBoardingFirstRoute
import com.yapp.fitrun.feature.onboarding.OnBoardingFourthRoute
import com.yapp.fitrun.feature.onboarding.OnBoardingResultRoute
import com.yapp.fitrun.feature.onboarding.OnBoardingSecondRoute
import com.yapp.fitrun.feature.onboarding.OnBoardingThirdRoute
import com.yapp.fitrun.feature.onboarding.viewmodel.OnBoardingViewModel
import kotlinx.serialization.Serializable

@Serializable
data object OnBoardingRoute

@Serializable
data object OnBoardingFirstRoute

@Serializable
data object OnBoardingSecondRoute

@Serializable
data object OnBoardingThirdRoute

@Serializable
data object OnBoardingFourthRoute

@Serializable
data object OnBoardingResultRoute


fun NavController.navigateToOnBoardingFirst() {
    navigate(OnBoardingFirstRoute)
}

fun NavController.navigateToOnBoardingSecond() {
    navigate(OnBoardingSecondRoute)
}

fun NavController.navigateToOnBoardingThird() {
    navigate(OnBoardingThirdRoute)
}

fun NavController.navigateToOnBoardingFourth() {
    navigate(OnBoardingFourthRoute)
}

fun NavController.navigateToOnBoardingResult() {
    navigate(OnBoardingResultRoute)
}

fun NavGraphBuilder.onBoardingNavGraph(
    navController: NavController,
    onBackClick: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToRoutine: () -> Unit,
    onNavigateToOnBoardingSecond: () -> Unit,
    onNavigateToOnBoardingThird: () -> Unit,
    onNavigateToOnBoardingFourth: () -> Unit,
    onNavigateToOnBoardingResult: () -> Unit,
) {
    navigation<OnBoardingRoute>(
        startDestination = OnBoardingFirstRoute
    ) {
        composable<OnBoardingFirstRoute> { entry ->
            val viewModel: OnBoardingViewModel = hiltViewModel()

            OnBoardingFirstRoute(
                onNavigateToOnBoardingSecond = onNavigateToOnBoardingSecond,
                viewModel = viewModel
            )
        }

        composable<OnBoardingSecondRoute> { entry ->
            val viewModel = entry.sharedViewModel<OnBoardingViewModel>(navController)

            OnBoardingSecondRoute(
                onBackClick = onBackClick,
                onNavigateToOnBoardingThird = onNavigateToOnBoardingThird,
                viewModel = viewModel
            )
        }

        composable<OnBoardingThirdRoute> { entry ->
            val viewModel = entry.sharedViewModel<OnBoardingViewModel>(navController)

            OnBoardingThirdRoute(
                onBackClick = onBackClick,
                onNavigateToOnBoardingFourth = onNavigateToOnBoardingFourth,
                viewModel = viewModel
            )
        }

        composable<OnBoardingFourthRoute> { entry ->
            val viewModel = entry.sharedViewModel<OnBoardingViewModel>(navController)

            OnBoardingFourthRoute(
                onBackClick = onBackClick,
                onNavigateToOnBoardingResult = onNavigateToOnBoardingResult,
                viewModel = viewModel
            )
        }

        composable<OnBoardingResultRoute> { entry ->
            val viewModel = entry.sharedViewModel<OnBoardingViewModel>(navController)

            OnBoardingResultRoute(
                navigateToHome = onNavigateToHome,
                navigateToRoutine = onNavigateToRoutine,
                viewModel = viewModel
            )
        }
    }
}