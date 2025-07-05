package com.yapp.fitrun.feature.login.viewmodel


// Side Effects
sealed class LoginSideEffect {
    data class ShowError(val message: String) : LoginSideEffect()
    data class NavigateToMain(val userId: Long) : LoginSideEffect()
    data class NavigateToOnboarding(val userId: Long) : LoginSideEffect()
}