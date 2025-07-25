package com.yapp.fitrun.feature.main

import androidx.annotation.Keep
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.yapp.fitrun.feature.crew.navigateToCrew
import com.yapp.fitrun.feature.home.HomeRoute
import com.yapp.fitrun.feature.home.navigateToHome
import com.yapp.fitrun.feature.mypage.navigateToMyPage
import com.yapp.fitrun.feature.onboarding.navigation.navigateToOnBoardingFourth
import com.yapp.fitrun.feature.onboarding.navigation.navigateToOnBoardingResult
import com.yapp.fitrun.feature.onboarding.navigation.navigateToOnBoardingSecond
import com.yapp.fitrun.feature.onboarding.navigation.navigateToOnBoardingThird
import com.yapp.fitrun.feature.record.navigateToRecord
import com.yapp.fitrun.feature.running.navigateToPlaying
import com.yapp.fitrun.feature.running.navigateToReady
import com.yapp.fitrun.feature.setgoal.navigation.navigateToSetGoal

@Keep
internal class MainNavigator(
    val navController: NavHostController,
    val startDestination: Any,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTab: MainTab?
        @Composable get() = MainTab.entries.firstOrNull { tab ->
            currentDestination?.hasRoute(route = tab.route) == true
        }

    fun navigate(tab: MainTab, route: Any? = null) {
        val id =
            navController.graph.findNode(route)?.id ?: navController.graph.findStartDestination().id
        // 온보딩 테스트 용도로 route 변경
        val startDestination = HomeRoute

        val navOptions = navOptions {
            popUpTo(id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainTab.HOME -> navController.navigateToHome(navOptions)
            MainTab.RECORD -> navController.navigateToRecord(navOptions)
            MainTab.CREW -> navController.navigateToCrew(navOptions)
            MainTab.MY_PAGE -> navController.navigateToMyPage(navOptions)
        }
    }

    @Composable
    fun shouldShowBottomBar(): Boolean {
        return MainTab.entries.any { tab ->
            currentDestination?.hasRoute(tab.route) == true
        }
    }

    fun navigateToOnBoardingSecond() {
        navController.navigateToOnBoardingSecond()
    }

    fun navigateToOnBoardingThird() {
        navController.navigateToOnBoardingThird()
    }

    fun navigateToOnBoardingFourth() {
        navController.navigateToOnBoardingFourth()
    }

    fun navigateToOnBoardingResult() {
        navController.navigateToOnBoardingResult()
    }

    fun navigateToRunning() {
        navController.navigateToReady()
//        navController.navigateToPlaying()
    }

    fun navigateToSetGoal() {
        navController.navigateToSetGoal()
    }

    fun navigateToPlaying() {
        navController.navigateToPlaying()
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
    startDestination: Any,
): MainNavigator = remember(navController, startDestination) {
    MainNavigator(navController, startDestination)
}
