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
import com.yapp.fitrun.feature.record.navigateToRecord

@Keep
internal class MainNavigator(
    val navController: NavHostController
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTab: MainTab?
        @Composable get() = MainTab.entries.firstOrNull { tab ->
            currentDestination?.hasRoute(route = tab.route) == true
        }

    val startDestination = HomeRoute

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
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
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController()
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}