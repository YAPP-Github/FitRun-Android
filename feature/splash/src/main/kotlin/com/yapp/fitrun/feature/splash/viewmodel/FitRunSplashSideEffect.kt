package com.yapp.fitrun.feature.splash.viewmodel

sealed interface FitRunSplashSideEffect {
    data object ValidateToken : FitRunSplashSideEffect
    data object AutoLoginSuccess : FitRunSplashSideEffect
    data object AutoLoginFail : FitRunSplashSideEffect
}
