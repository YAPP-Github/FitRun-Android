package com.yapp.fitrun.feature.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.yapp.fitrun.core.common.LoginNavigator
import com.yapp.fitrun.feature.home.HomeRoute
import com.yapp.fitrun.feature.navigator.INavigator
import com.yapp.fitrun.feature.onboarding.navigation.OnBoardingRoute
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.core.net.toUri

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @LoginNavigator
    @Inject
    lateinit var loginNavigator: INavigator

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
            val navigator: MainNavigator = rememberMainNavigator(
                startDestination = startDestination,
                navigateToLogin = {
                    loginNavigator.navigateFrom(
                        activity = this,
                        withFinish = true,
                    )
                },
                navigateToPermission = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.data = "package:com.yapp.fitrun".toUri()
                    startActivity(intent)
                },
            )
            MainScreen(navigator = navigator)
        }
    }
}
