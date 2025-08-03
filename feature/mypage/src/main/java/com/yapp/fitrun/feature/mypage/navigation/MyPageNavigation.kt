package com.yapp.fitrun.feature.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.yapp.fitrun.core.common.extension.sharedViewModel
import com.yapp.fitrun.feature.mypage.ChangeRunningLevelRoute
import com.yapp.fitrun.feature.mypage.ChangeRunningPurposeRoute
import com.yapp.fitrun.feature.mypage.MyPageRoute
import com.yapp.fitrun.feature.mypage.viewmodel.MyPageViewModel
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

fun NavController.navigateToChangeRunningLevel() {
    navigate(ChangeRunningLevelRoute)
}

fun NavController.navigateToChangeRunningPurpose() {
    navigate(ChangeRunningPurposeRoute)
}

fun NavGraphBuilder.myPageNavGraph(
    navController: NavController,
    onBackClick: () -> Unit,
    onNavigateToChangeRunningLevel: () -> Unit,
    onNavigateToChangeRunningPurpose: () -> Unit,
    padding: PaddingValues,
) {
    navigation<MyPageSettingRoute>(
        startDestination = MyPageRoute,
    ) {
        composable<MyPageRoute> { entry ->
            val viewModel = entry.sharedViewModel<MyPageViewModel>(navController)

            MyPageRoute(
                padding = padding,
                onNavigateToChangeRunningLevel = onNavigateToChangeRunningLevel,
                onNavigateToChangeRunningPurpose = onNavigateToChangeRunningPurpose,
                viewModel = viewModel,
            )
        }

        composable<ChangeRunningLevelRoute> { entry ->
            val viewModel = entry.sharedViewModel<MyPageViewModel>(navController)

            ChangeRunningLevelRoute(
                padding = padding,
                onBackClick = onBackClick,
                viewModel = viewModel,
            )
        }

        composable<ChangeRunningPurposeRoute> { entry ->
            val viewModel = entry.sharedViewModel<MyPageViewModel>(navController)

            ChangeRunningPurposeRoute(
                padding = padding,
                onBackClick = onBackClick,
                viewModel = viewModel,
            )
        }
    }
}
