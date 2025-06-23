package com.yapp.fitrun.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.yapp.fitrun.feature.login.navigation.LoginRoute

internal class MainNavigator(
    val navController: NavHostController
){
    val startDestination = LoginRoute

}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController()
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}