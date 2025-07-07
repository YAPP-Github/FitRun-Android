package com.yapp.fitrun.feature.main

import androidx.annotation.Keep
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yapp.fitrun.feature.home.HOME_ROUTE
import com.yapp.fitrun.feature.home.navigateToHome

@Keep
internal class MainNavigator(
    val navController: NavHostController
){
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = HOME_ROUTE

    fun navigateToHome() {
        navController.navigateToHome()
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController()
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}