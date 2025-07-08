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
import com.yapp.fitrun.feature.onboarding.navigation.ON_BOARDING_FIRST_ROUTE
import com.yapp.fitrun.feature.onboarding.navigation.ON_BOARDING_ROUTE
import com.yapp.fitrun.feature.onboarding.navigation.navigateToOnBoardingFirst
import com.yapp.fitrun.feature.onboarding.navigation.navigateToOnBoardingFourth
import com.yapp.fitrun.feature.onboarding.navigation.navigateToOnBoardingResult
import com.yapp.fitrun.feature.onboarding.navigation.navigateToOnBoardingSecond
import com.yapp.fitrun.feature.onboarding.navigation.navigateToOnBoardingThird

@Keep
internal class MainNavigator(
    val navController: NavHostController
){
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    // 온보딩 테스트 용도로 route 변경
    val startDestination = ON_BOARDING_ROUTE

    fun navigateToHome() {
        navController.navigateToHome()
    }

    fun navigateToOnBoardingFirst() {
        navController.navigateToOnBoardingFirst()
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
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController()
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}