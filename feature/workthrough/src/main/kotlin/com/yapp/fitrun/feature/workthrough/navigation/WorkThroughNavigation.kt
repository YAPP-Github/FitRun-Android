package com.yapp.fitrun.feature.workthrough.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.yapp.fitrun.feature.workthrough.WorkThroughRoute

const val WORK_THROUGH_ROUTE = "work_through_route"

fun NavController.navigateToWorkThrough(navOptions: NavOptions) {
    navigate(WORK_THROUGH_ROUTE, navOptions)
}

fun NavGraphBuilder.workThroughNavGraph(
    navigateToLogin: () -> Unit,
) {
    composable(route = WORK_THROUGH_ROUTE) {
        WorkThroughRoute(
            navigateToLogin = navigateToLogin,
        )
    }
}
