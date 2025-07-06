package com.yapp.fitrun.feature.main

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.yapp.fitrun.feature.main.component.MainNavHost

@Composable
internal fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
) {

    MainScreenContent(navigator = navigator)

}

@Composable
private fun MainScreenContent(
    navigator: MainNavigator
) {
    Scaffold { innerPadding ->
        MainNavHost(
            navigator = navigator,
            paddingValues = innerPadding
        )
    }
}