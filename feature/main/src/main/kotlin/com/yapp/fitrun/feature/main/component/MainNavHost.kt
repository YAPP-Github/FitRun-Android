package com.yapp.fitrun.feature.main.component

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.yapp.fitrun.feature.crew.crewNavGraph
import com.yapp.fitrun.feature.home.homeNavGraph
import com.yapp.fitrun.feature.main.MainNavigator
import com.yapp.fitrun.feature.main.MainTab
import com.yapp.fitrun.feature.mypage.myPageNavGraph
import com.yapp.fitrun.feature.onboarding.navigation.OnBoardingRoute
import com.yapp.fitrun.feature.onboarding.navigation.onBoardingNavGraph
import com.yapp.fitrun.feature.record.navigateToRecordDetail
import com.yapp.fitrun.feature.record.recordNavGraph
import com.yapp.fitrun.feature.running.runningNavGraph
import com.yapp.fitrun.feature.running.runningonboarding.runningOnBoardingNavGraph
import com.yapp.fitrun.feature.setgoal.navigation.setGoalNavGraph

@Composable
internal fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    padding: PaddingValues,
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        NavHost(
            navController = navigator.navController,
            startDestination = navigator.startDestination,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(400),
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(500),
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(400),
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(500),
                )
            },
        ) {
            homeNavGraph(
                padding = padding,
                onNavigateToRunning = navigator::navigateToRunningOnBoardingFirst, // For UT
                onNavigateToSetGoal = navigator::navigateToSetGoalOnBoarding,
            )
            recordNavGraph(
                onBackClick = { navigator.navController.popBackStack() },
                onNavigateToSetGoalOnBoarding = navigator::navigateToSetGoalOnBoarding,
                padding = padding,
                onNavigateToRecordDetail = navigator::navigateToRecordDetail,
            )
            crewNavGraph(
                padding = padding,
            )
            myPageNavGraph(padding = padding)
            onBoardingNavGraph(
                padding = padding,
                navController = navigator.navController,
                onBackClick = { navigator.navController.popBackStack() },
                onNavigateToHome = {
                    navigator.navigate(MainTab.HOME, OnBoardingRoute)
                },
                onNavigateToOnBoardingSecond = navigator::navigateToOnBoardingSecond,
                onNavigateToOnBoardingThird = navigator::navigateToOnBoardingThird,
                onNavigateToOnBoardingFourth = navigator::navigateToOnBoardingFourth,
                onNavigateToOnBoardingResult = navigator::navigateToOnBoardingResult,
            )
            runningOnBoardingNavGraph(
                padding = padding,
                navController = navigator.navController,
                onBackClick = { navigator.navController.popBackStack() },
                onNavigateToReady = navigator::navigateToRunning,
                onNavigateToRunningOnBoardingSecond = navigator::navigateToRunningOnBoardingSecond,
                onNavigateToRunningOnBoardingThird = navigator::navigateToRunningOnBoardingThird,
            )
            runningNavGraph(
                padding = padding,
                onNavigateToPlay = navigator::navigateToPlaying,
                onNavigateToSetGoalOnBoarding = { navigator.navigateToRecordDetail(1) },
            )
            setGoalNavGraph(
                padding = padding,
                onBackClick = { navigator.navController.popBackStack() },
                onNavigateToRecordDetail = { recordId ->
                    navigator.navController.navigateToRecordDetail(recordId)
                },
            )
        }
    }
}
