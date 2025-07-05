package com.yapp.fitrun.feature.splash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.yapp.fitrun.core.common.LoginNavigator
import com.yapp.fitrun.core.common.MainNavigator
import com.yapp.fitrun.feature.navigator.INavigator
import com.yapp.fitrun.feature.splash.viewmodel.FitRunSplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FitRunSplashActivity : ComponentActivity() {
    private val viewModel: FitRunSplashViewModel by viewModels()

    @LoginNavigator
    @Inject
    lateinit var loginNavigator: INavigator

    @MainNavigator
    @Inject
    lateinit var mainNavigator: INavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            FitRunsSplashRoute(
                navigateToLogin = {
                    loginNavigator.navigateFrom(
                        activity = this,
                        withFinish = true,
                    )
                },
                navigateToMain = {
                    mainNavigator.navigateFrom(
                        activity = this,
                        withFinish = true,
                    )
                },
                viewModel = viewModel
            )
        }
    }
}