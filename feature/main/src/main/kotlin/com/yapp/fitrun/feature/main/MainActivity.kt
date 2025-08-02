package com.yapp.fitrun.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.yapp.fitrun.feature.home.HomeRoute
import com.yapp.fitrun.feature.onboarding.navigation.OnBoardingRoute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.Transparent.toArgb(),
                Color.Transparent.toArgb(),
            ),
            navigationBarStyle = SystemBarStyle.light(
                Color.Transparent.toArgb(),
                Color.Transparent.toArgb(),
            ),
        )

        val isNew = intent.getBooleanExtra("isNew", false)
        val startDestination: Any = if (isNew) OnBoardingRoute else HomeRoute

        setContent {
            val navigator: MainNavigator = rememberMainNavigator(startDestination = startDestination)
            MainScreen(navigator = navigator)
        }
    }
}
