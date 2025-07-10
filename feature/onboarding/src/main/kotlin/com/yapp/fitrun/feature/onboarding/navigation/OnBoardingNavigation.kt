package com.yapp.fitrun.feature.onboarding.navigation

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

const val ON_BOARDING_ROUTE = "on_boarding_route"
const val ON_BOARDING_FIRST_ROUTE = "on_boarding_first_route"
const val ON_BOARDING_SECOND_ROUTE = "on_boarding_second_route"
const val ON_BOARDING_THIRD_ROUTE = "on_boarding_third_route"
const val ON_BOARDING_FOURTH_ROUTE = "on_boarding_fourth_route"
const val ON_BOARDING_RESULT_ROUTE = "on_boarding_result_route"

fun NavController.navigateToOnBoardingFirst() {
    navigate(ON_BOARDING_FIRST_ROUTE)
}

fun NavController.navigateToOnBoardingSecond() {
    navigate(ON_BOARDING_SECOND_ROUTE)
}

fun NavController.navigateToOnBoardingThird() {
    navigate(ON_BOARDING_THIRD_ROUTE)
}

fun NavController.navigateToOnBoardingFourth() {
    navigate(ON_BOARDING_FOURTH_ROUTE)
}

fun NavController.navigateToOnBoardingResult() {
    navigate(ON_BOARDING_RESULT_ROUTE)
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
    navigation(
        startDestination = ON_BOARDING_FIRST_ROUTE,
        route = ON_BOARDING_ROUTE,
    ) {
        composable(route = ON_BOARDING_FIRST_ROUTE) { entry ->
            val viewModel = entry.sharedViewModel<OnBoardingViewModel>(navController)

            OnBoardingFirstRoute(
                onNavigateToOnBoardingSecond = onNavigateToOnBoardingSecond,
                viewModel = viewModel
            )
        }

        composable(route = ON_BOARDING_SECOND_ROUTE) { entry ->
            val viewModel = entry.sharedViewModel<OnBoardingViewModel>(navController)

            OnBoardingSecondRoute(
                onBackClick = onBackClick,
                onNavigateToOnBoardingThird = onNavigateToOnBoardingThird,
                viewModel = viewModel
            )
        }

        composable(route = ON_BOARDING_THIRD_ROUTE) { entry ->
            val viewModel = entry.sharedViewModel<OnBoardingViewModel>(navController)

            OnBoardingThirdRoute(
                onBackClick = onBackClick,
                onNavigateToOnBoardingFourth = onNavigateToOnBoardingFourth,
                viewModel = viewModel
            )
        }

        composable(route = ON_BOARDING_FOURTH_ROUTE) { entry ->
            val viewModel = entry.sharedViewModel<OnBoardingViewModel>(navController)

            OnBoardingFourthRoute(
                onBackClick = onBackClick,
                onNavigateToOnBoardingResult = onNavigateToOnBoardingResult,
                viewModel = viewModel
            )
        }

        composable(route = ON_BOARDING_RESULT_ROUTE) { entry ->
            val viewModel = entry.sharedViewModel<OnBoardingViewModel>(navController)

            OnBoardingResultRoute(
                navigateToHome = onNavigateToHome,
                navigateToRoutine = onNavigateToRoutine,
                viewModel = viewModel
            )
        }
    }
}