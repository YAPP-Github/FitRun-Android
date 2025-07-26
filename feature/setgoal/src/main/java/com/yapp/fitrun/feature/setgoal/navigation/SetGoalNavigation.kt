package com.yapp.fitrun.feature.setgoal.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yapp.fitrun.feature.setgoal.SetGoalRoute
import kotlinx.serialization.Serializable

@Serializable
data object SetGoalRoute // route to ForYou screen

fun NavController.navigateToSetGoal() {
    navigate(SetGoalRoute)
}

fun NavGraphBuilder.setGoalNavGraph() {
    composable<SetGoalRoute> {
        SetGoalRoute()
    }
}
