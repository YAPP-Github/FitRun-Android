package com.yapp.fitrun.feature.splash.viewmodel

sealed interface FitRunSplashSideEffect {
    data object ValidateToken : FitRunSplashSideEffect
    data class AutoLoginSuccess(val userId: Long) : FitRunSplashSideEffect
    data object AutoLoginFail : FitRunSplashSideEffect
}