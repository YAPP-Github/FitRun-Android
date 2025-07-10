package com.yapp.fitrun.feature.mypage

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object MyPageRoute // route to ForYou screen

fun NavController.navigateToMyPage(navOptions: NavOptions) {
    navigate(MyPageRoute, navOptions)
}

fun NavGraphBuilder.myPageNavGraph(
    padding: PaddingValues
) {
    composable<MyPageRoute> {
        MyPageRoute(
            padding = padding
            // TODO: viewmodel
        )
    }
}