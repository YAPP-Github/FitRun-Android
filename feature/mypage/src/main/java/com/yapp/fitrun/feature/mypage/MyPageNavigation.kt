package com.yapp.fitrun.feature.mypage

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import kotlinx.serialization.Serializable

@Serializable
data object MyPageSettingRoute

@Serializable
data object MyPageRoute

@Serializable
data object ChangeRunningLevelRoute

@Serializable
data object ChangeRunningPurposeRoute

fun NavController.navigateToMyPage(navOptions: NavOptions) {
    navigate(MyPageRoute, navOptions)
}

fun NavController.navigateToChangeRunningLevelRoute() {
    navigate(ChangeRunningLevelRoute)
}

fun NavController.navigateToChangeRunningPurposeRoute() {
    navigate(ChangeRunningPurposeRoute)
}

fun NavGraphBuilder.myPageNavGraph(
    padding: PaddingValues,
) {
    navigation<MyPageSettingRoute>(
        startDestination = MyPageRoute,
    ) {
        composable<MyPageRoute> {
            MyPageRoute(
                padding = padding,
                // TODO: viewmodel
            )
        }

        composable<ChangeRunningLevelRoute> {
            ChangeRunningLevelRoute(
                padding = padding,
                // TODO: viewmodel
            )
        }

        composable<ChangeRunningPurposeRoute> {
            ChangeRunningLevelRoute(
                padding = padding,
                // TODO: viewmodel
            )
        }
    }
}
