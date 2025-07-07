package com.yapp.fitrun.feature.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val HOME_ROUTE = "home_route"

fun NavController.navigateToHome() {
    navigate(HOME_ROUTE)
}

fun NavGraphBuilder.homeNavGraph(
    padding: PaddingValues
) {
    composable(route = HOME_ROUTE) {
        HomeRoute(
            padding = padding
            // TODO: viewmodel
        )
    }
}