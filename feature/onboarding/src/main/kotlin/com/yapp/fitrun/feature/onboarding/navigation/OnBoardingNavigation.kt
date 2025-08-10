package com.yapp.fitrun.feature.onboarding.navigation

import androidx.compose.foundation.layout.PaddingValues
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
    padding: PaddingValues,
    navController: NavController,
    onBackClick: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToOnBoardingSecond: () -> Unit,
    onNavigateToOnBoardingThird: () -> Unit,
    onNavigateToOnBoardingFourth: () -> Unit,
    onNavigateToOnBoardingResult: () -> Unit,
) {
    navigation<OnBoardingRoute>(
        startDestination = OnBoardingResultRoute, // FOR UT
    ) {
        composable<OnBoardingFirstRoute> { entry ->
            val viewModel = entry.sharedViewModel<OnBoardingViewModel>(navController)

            OnBoardingFirstRoute(
                padding = padding,
                onNavigateToOnBoardingSecond = onNavigateToOnBoardingSecond,
                viewModel = viewModel,
            )
        }

        composable<OnBoardingSecondRoute> { entry ->
            val viewModel = entry.sharedViewModel<OnBoardingViewModel>(navController)

            OnBoardingSecondRoute(
                padding = padding,
                onBackClick = onBackClick,
                onNavigateToOnBoardingThird = onNavigateToOnBoardingThird,
                viewModel = viewModel,
            )
        }

        composable<OnBoardingThirdRoute> { entry ->
            val viewModel = entry.sharedViewModel<OnBoardingViewModel>(navController)

            OnBoardingThirdRoute(
                padding = padding,
                onBackClick = onBackClick,
                onNavigateToOnBoardingFourth = onNavigateToOnBoardingFourth,
                viewModel = viewModel,
            )
        }

        composable<OnBoardingFourthRoute> { entry ->
            val viewModel = entry.sharedViewModel<OnBoardingViewModel>(navController)

            OnBoardingFourthRoute(
                padding = padding,
                onBackClick = onBackClick,
                onNavigateToOnBoardingResult = onNavigateToOnBoardingResult,
                viewModel = viewModel,
            )
        }

        composable<OnBoardingResultRoute> { entry ->
            val viewModel = entry.sharedViewModel<OnBoardingViewModel>(navController)

            OnBoardingResultRoute(
                padding = padding,
                navigateToHome = onNavigateToHome,
                viewModel = viewModel,
            )
        }
    }
}
