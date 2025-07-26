package com.yapp.fitrun.feature.crew

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object CrewRoute // route to ForYou screen

fun NavController.navigateToCrew(navOptions: NavOptions) {
    navigate(CrewRoute, navOptions)
}

fun NavGraphBuilder.crewNavGraph() {
    composable<CrewRoute> {
        CrewRoute(
            // TODO: viewmodel
        )
    }
}
