package com.yapp.fitrun.feature.setgoal.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yapp.fitrun.feature.setgoal.SetGoalOnBoardingRoute
import com.yapp.fitrun.feature.setgoal.SetGoalRoute
import kotlinx.serialization.Serializable

@Serializable
data object SetGoalRoute

@Serializable
data object SetGoalOnBoardingRoute

fun NavController.navigateToSetGoal() {
    navigate(SetGoalRoute)
}

fun NavController.navigateToSetGoalOnBoarding() {
    navigate(SetGoalOnBoardingRoute)
}

fun NavGraphBuilder.setGoalNavGraph(
    padding: PaddingValues,
    onBackClick: () -> Unit,
) {
    composable<SetGoalRoute> {
        SetGoalRoute(
            padding = padding,
            onBackClick = onBackClick,
        )
    }
    composable<SetGoalOnBoardingRoute> {
        SetGoalOnBoardingRoute(
            onBackClick = onBackClick,
            padding = padding,
            onNavigateToComplete = {},
        )
    }
}
