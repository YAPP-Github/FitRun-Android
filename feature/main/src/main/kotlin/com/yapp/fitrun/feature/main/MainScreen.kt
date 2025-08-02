package com.yapp.fitrun.feature.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import com.yapp.fitrun.feature.main.component.MainBottomBar
import com.yapp.fitrun.feature.main.component.MainNavHost

@Composable
internal fun MainScreen(
    navigator: MainNavigator,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val navigationHeight = with(LocalDensity.current) {
        WindowInsets.systemBars.getBottom(this).toDp()
    }

    Scaffold(
        content = { padding ->
            MainNavHost(
                modifier = Modifier.fillMaxSize(),
                navigator = navigator,
                padding = padding,
            )
        },
        bottomBar = {
            MainBottomBar(
                visible = navigator.shouldShowBottomBar(),
                tabs = MainTab.entries.toList(),
                currentTab = navigator.currentTab,
                onTabSelected = { navigator.navigate(it) },
                navBarHeight = navigationHeight,
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
        containerColor = MaterialTheme.colorScheme.background,
    )
}
