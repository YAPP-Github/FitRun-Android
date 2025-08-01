package com.yapp.fitrun.feature.record

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data object RecordRoute

@Serializable
data class RecordDetailRoute(val recordId: Int)

fun NavController.navigateToRecord(navOptions: NavOptions) {
    navigate(RecordRoute, navOptions)
}

fun NavController.navigateToRecordDetail(recordId: Int) {
    navigate(RecordDetailRoute(recordId))
}

fun NavGraphBuilder.recordNavGraph(
    onBackClick: () -> Unit,
    onNavigateToSetGoalOnBoarding: () -> Unit,
    onNavigateToRecordDetail: (Int) -> Unit,
    padding: PaddingValues,
) {
    composable<RecordRoute> {
        RecordRoute(
            padding = padding,
            onNavigateToRecordDetail = onNavigateToRecordDetail,
        )
    }

    composable<RecordDetailRoute> { backStackEntry ->
        val recordDetailRoute = backStackEntry.toRoute<RecordDetailRoute>()

        RecordDetailRoute(
            onBackClick = onBackClick,
            onNavigateToSetGoalOnBoarding = onNavigateToSetGoalOnBoarding,
            recordId = recordDetailRoute.recordId,
            padding = padding,
        )
    }
}
