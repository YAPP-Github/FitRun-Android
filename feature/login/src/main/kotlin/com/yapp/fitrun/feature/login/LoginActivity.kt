package com.yapp.fitrun.feature.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.yapp.fitrun.core.common.MainNavigator
import com.yapp.fitrun.feature.login.viewmodel.LoginViewModel
import com.yapp.fitrun.feature.navigator.INavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val viewModel: LoginViewModel by viewModels()

    @MainNavigator
    @Inject
    lateinit var mainNavigator: INavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoginScreen(
                viewModel = viewModel,
                navigateToHome = { isNew ->
                    mainNavigator.navigateFrom(
                        activity = this,
                        withFinish = true,
                        intentBuilder = { putExtra("isNew", isNew) }
                    )
                }
            )
        }
    }
}