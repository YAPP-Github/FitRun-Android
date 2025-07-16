package com.yapp.fitrun.feature.main.component

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
import com.yapp.fitrun.feature.onboarding.navigation.onBoardingNavGraph
import com.yapp.fitrun.feature.record.recordNavGraph
import com.yapp.fitrun.feature.running.runningNavGraph

@Composable
internal fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    padding: PaddingValues,
) {

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        NavHost(
            navController = navigator.navController,
            startDestination = navigator.startDestination
        ) {
            homeNavGraph(
                padding = padding,
                onNavigateToRunning = navigator::navigateToRunning
            )
            recordNavGraph(padding = padding)
            crewNavGraph(padding = padding)
            myPageNavGraph(padding = padding)
            onBoardingNavGraph(
                navController = navigator.navController,
                onBackClick = { navigator.navController.popBackStack() },
                onNavigateToHome = { navigator.navigate(MainTab.HOME) },
                onNavigateToRoutine = { /* TODO */ },
                onNavigateToOnBoardingSecond = navigator::navigateToOnBoardingSecond,
                onNavigateToOnBoardingThird = navigator::navigateToOnBoardingThird,
                onNavigateToOnBoardingFourth = navigator::navigateToOnBoardingFourth,
                onNavigateToOnBoardingResult = navigator::navigateToOnBoardingResult,
            )
            runningNavGraph(onNavigateToPlay = navigator::navigateToPlaying)
        }
    }
}