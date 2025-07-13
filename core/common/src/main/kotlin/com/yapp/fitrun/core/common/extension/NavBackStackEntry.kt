package com.yapp.fitrun.core.common.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

// 같은 NavGraph 에서 viewmodel을 공유하기 위함
// https://velog.io/@kej_ad/Android-Compsoe-Jetpack-Navigation-Nested-Graph와-Shared-ViewModel#navigation에서-viewmodelstoreowner
@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: destination.route
    val parentEntry = remember(this) {
        if (destination.parent != null && navGraphRoute != null) {
            navController.getBackStackEntry(navGraphRoute)
        } else {
            this
        }
    }
    
    return hiltViewModel(parentEntry)
}