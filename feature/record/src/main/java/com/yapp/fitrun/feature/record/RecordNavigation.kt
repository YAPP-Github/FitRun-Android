package com.yapp.fitrun.feature.record

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object RecordRoute // route to ForYou screen

fun NavController.navigateToRecord(navOptions: NavOptions) {
    navigate(RecordRoute, navOptions)
}

fun NavGraphBuilder.recordNavGraph(
    padding: PaddingValues,
) {
    composable<RecordRoute> {
        RecordRoute(
            padding = padding,
            // TODO: viewmodel
        )
    }
}
