package com.yapp.fitrun.feature.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.yapp.fitrun.core.common.extension.sharedViewModel
import com.yapp.fitrun.feature.mypage.setting.ChangeRunningLevelRoute
import com.yapp.fitrun.feature.mypage.setting.ChangeRunningPurposeRoute
import com.yapp.fitrun.feature.mypage.MyPageRoute
import com.yapp.fitrun.feature.mypage.profile.ConfirmWithdrawRoute
import com.yapp.fitrun.feature.mypage.profile.ProfileRoute
import com.yapp.fitrun.feature.mypage.profile.WithdrawRoute
import com.yapp.fitrun.feature.mypage.setting.ChangeNotificationRoute
import com.yapp.fitrun.feature.mypage.setting.ChangeRunningSettingRoute
import com.yapp.fitrun.feature.mypage.service.ServiceUsageRoute
import com.yapp.fitrun.feature.mypage.service.TermsAndConditionsRoute
import com.yapp.fitrun.feature.mypage.setting.ChangeRunningTimeDistanceGoalRoute
import com.yapp.fitrun.feature.mypage.viewmodel.MyPageViewModel
import kotlinx.serialization.Serializable

@Serializable
data object MyPageSettingRoute

@Serializable
data object MyPageRoute

@Serializable
data class ChangeRunningTimeDistanceGoalRoute(val initialPage: Int)

// setting
@Serializable
data object ChangeRunningLevelRoute

@Serializable
data object ChangeRunningPurposeRoute

@Serializable
data object ChangeRunningSettingRoute

@Serializable
data object ChangeNotificationRoute

@Serializable
data object TermsAndConditionsRoute

@Serializable
data object ServiceUsageRoute

// profile
@Serializable
data object ProfileRoute

@Serializable
data object WithdrawRoute

@Serializable
data object ConfirmWithdrawRoute

fun NavController.navigateToMyPage(navOptions: NavOptions) {
    navigate(MyPageRoute, navOptions)
}

fun NavController.navigateToChangeRunningTimeDistanceGoal(initialPage: Int) {
    navigate(ChangeRunningTimeDistanceGoalRoute(initialPage))
}

// setting
fun NavController.navigateToChangeRunningLevel() {
    navigate(ChangeRunningLevelRoute)
}

fun NavController.navigateToChangeRunningPurpose() {
    navigate(ChangeRunningPurposeRoute)
}

fun NavController.navigateChangeRunningSetting() {
    navigate(ChangeRunningSettingRoute)
}

fun NavController.navigateToChangeNotifications() {
    navigate(ChangeNotificationRoute)
}

fun NavController.navigateToTermsAndConditions() {
    navigate(TermsAndConditionsRoute)
}

fun NavController.navigateToServiceUsage() {
    navigate(ServiceUsageRoute)
}

// profile
fun NavController.onNavigateToProfile() {
    navigate(ProfileRoute)
}

fun NavController.onNavigateToWithdraw() {
    navigate(WithdrawRoute)
}

fun NavController.onNavigateToConfirmWithdraw() {
    navigate(ConfirmWithdrawRoute)
}

fun NavGraphBuilder.myPageNavGraph(
    navController: NavController,
    onBackClick: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onWithdrawClick: () -> Unit,
    onNavigateToConfirmWithdraw: () -> Unit,
    onNavigateToChangeRunningLevel: () -> Unit,
    onNavigateToChangeRunningPurpose: () -> Unit,
    onNavigateChangeRunningSetting: () -> Unit,
    onNavigateToChangeNotifications: () -> Unit,
    onNavigateToTermsAndConditions: () -> Unit,
    onNavigateToServiceUsage: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToPermission: () -> Unit,
    onNavigateToChangeRunningTimeDistanceGoal: (Int) -> Unit,
    onNavigateToSetGoal: () -> Unit,
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
                onNavigateChangeRunningSetting = onNavigateChangeRunningSetting,
                onNavigateToChangeNotifications = onNavigateToChangeNotifications,
                onNavigateToTermsAndConditions = onNavigateToTermsAndConditions,
                onNavigateToServiceUsage = onNavigateToServiceUsage,
                onNavigateToLogin = onNavigateToLogin,
                onNavigateToPermission = onNavigateToPermission,
                onNavigateToSetGoal = onNavigateToSetGoal,
                onNavigateToChangeRunningTimeDistanceGoal = onNavigateToChangeRunningTimeDistanceGoal,
                viewModel = viewModel,
            )
        }

        composable<ChangeRunningTimeDistanceGoalRoute> { entry ->
            val viewModel = entry.sharedViewModel<MyPageViewModel>(navController)
            val args = entry.toRoute<ChangeRunningTimeDistanceGoalRoute>()

            ChangeRunningTimeDistanceGoalRoute(
                initialPage = args.initialPage,
                padding = padding,
                onBackClick = onBackClick,
                viewModel = viewModel,
            )
        }

        // setting
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

        composable<ChangeRunningSettingRoute> { entry ->
            val viewModel = entry.sharedViewModel<MyPageViewModel>(navController)

            ChangeRunningSettingRoute(
                onBackClick = onBackClick,
                viewModel = viewModel,
            )
        }

        composable<ChangeNotificationRoute> { entry ->
            val viewModel = entry.sharedViewModel<MyPageViewModel>(navController)

            ChangeNotificationRoute(
                onBackClick = onBackClick,
                viewModel = viewModel,
            )
        }

        composable<TermsAndConditionsRoute> { entry ->
            val viewModel = entry.sharedViewModel<MyPageViewModel>(navController)

            TermsAndConditionsRoute(
                onBackClick = onBackClick,
                viewModel = viewModel,
            )
        }

        composable<ServiceUsageRoute> { entry ->
            val viewModel = entry.sharedViewModel<MyPageViewModel>(navController)

            ServiceUsageRoute(
                onBackClick = onBackClick,
                viewModel = viewModel,
            )
        }

        // profile
        composable<ProfileRoute> { entry ->
            val viewModel = entry.sharedViewModel<MyPageViewModel>(navController)

            ProfileRoute(
                onWithdrawClick = onWithdrawClick,
                onBackClick = onBackClick,
                viewModel = viewModel,
                onLogoutClick = onNavigateToLogin,
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
