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
import com.yapp.fitrun.feature.mypage.profile.ConfirmWithdrawRoute
import com.yapp.fitrun.feature.mypage.profile.ProfileRoute
import com.yapp.fitrun.feature.mypage.profile.WithdrawRoute
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

@Serializable
data object ProfileRoute

@Serializable
data object WithdrawRoute

@Serializable
data object ConfirmWithdrawRoute

fun NavController.navigateToMyPage(navOptions: NavOptions) {
    navigate(MyPageRoute, navOptions)
}

fun NavController.onNavigateToProfile() {
    navigate(ProfileRoute)
}

fun NavController.onNavigateToWithdraw() {
    navigate(WithdrawRoute)
}

fun NavController.onNavigateToConfirmWithdraw() {
    navigate(ConfirmWithdrawRoute)
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
    onNavigateToProfile: () -> Unit,
    onWithdrawClick: () -> Unit,
    onNavigateToConfirmWithdraw: () -> Unit,
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
                onNavigateToProfile = onNavigateToProfile,
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

        composable<ProfileRoute> { entry ->
            val viewModel = entry.sharedViewModel<MyPageViewModel>(navController)

            ProfileRoute(
                padding = padding,
                onWithdrawClick = onWithdrawClick,
                onBackClick = onBackClick,
                viewModel = viewModel,
            )
        }

        composable<WithdrawRoute> { entry ->
            val viewModel = entry.sharedViewModel<MyPageViewModel>(navController)

            WithdrawRoute(
                viewModel = viewModel,
                padding = padding,
                onNextClick = onNavigateToConfirmWithdraw,
                onBackClick = onBackClick,
            )
        }

        composable<ConfirmWithdrawRoute> { entry ->
            val viewModel = entry.sharedViewModel<MyPageViewModel>(navController)

            ConfirmWithdrawRoute(
                viewModel = viewModel,
                padding = padding,
                onBackClick = onBackClick,
            )
        }
    }
}
