package com.yapp.fitrun.feature.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute // route to ForYou screen

fun NavController.navigateToHome(navOptions: NavOptions) {
    navigate(HomeRoute, navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues,
    onNavigateToRunning: () -> Unit,
    onNavigateToSetGoal: () -> Unit,
) {
    composable<HomeRoute> {
        HomeRoute(
            padding = padding,
            onNavigateToRunning = onNavigateToRunning,
            onNavigateToSetGoal = onNavigateToSetGoal,
        )
    }
}
