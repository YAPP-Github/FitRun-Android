package com.yapp.fitrun.feature.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.yapp.fitrun.feature.home.homeNavGraph
import com.yapp.fitrun.feature.main.component.MainBottomBar
import com.yapp.fitrun.feature.main.component.MainNavHost
import com.yapp.fitrun.feature.onboarding.navigation.onBoardingNavGraph

@Composable
internal fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        content = { padding ->
            NavHost(
                navController = navigator.navController,
                startDestination = navigator.startDestination,
                modifier = Modifier.fillMaxSize(),
            ) {
                homeNavGraph(
                    padding = padding
                )
                
                onBoardingNavGraph(
                    navController = navigator.navController,
                    onBackClick = { navigator.navController.popBackStack() },
                    onNavigateToHome = navigator::navigateToHome,
                    onNavigateToRoutine = { /* TODO */ },
                    onNavigateToOnBoardingSecond = navigator::navigateToOnBoardingSecond,
                    onNavigateToOnBoardingThird = navigator::navigateToOnBoardingThird,
                    onNavigateToOnBoardingFourth = navigator::navigateToOnBoardingFourth,
                    onNavigateToOnBoardingResult = navigator::navigateToOnBoardingResult,
                )
            }
        },
        bottomBar = {
            MainBottomBar(
                modifier = Modifier.navigationBarsPadding(),
                visible = navigator.shouldShowBottomBar(),
                tabs = MainTab.entries.toList(),
                currentTab = navigator.currentTab,
                onTabSelected = { navigator.navigate(it) }
            )
        },

        snackbarHost = { SnackbarHost(snackBarHostState) },
        containerColor = MaterialTheme.colorScheme.background,
    )

}