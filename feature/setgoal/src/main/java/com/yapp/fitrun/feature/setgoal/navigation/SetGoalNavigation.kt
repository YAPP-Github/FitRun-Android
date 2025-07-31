package com.yapp.fitrun.feature.setgoal.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yapp.fitrun.feature.setgoal.SetGoalRoute
import kotlinx.serialization.Serializable

@Serializable
data object SetGoalRoute

fun NavController.navigateToSetGoal() {
    navigate(SetGoalRoute)
}

fun NavGraphBuilder.setGoalNavGraph(
    padding: PaddingValues,
) {
    composable<SetGoalRoute> {
        SetGoalRoute(
            padding = padding,
        )
    }
}
