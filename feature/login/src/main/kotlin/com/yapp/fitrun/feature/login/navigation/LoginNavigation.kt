package com.yapp.fitrun.feature.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.yapp.fitrun.feature.login.LoginScreen
import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

fun NavGraphBuilder.loginNavGraph() {
    composable<LoginRoute> {
        LoginScreen()
    }
}
