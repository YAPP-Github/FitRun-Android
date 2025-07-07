package com.yapp.fitrun.feature.login.viewmodel


// Side Effects
sealed interface LoginSideEffect {
    data class LoginFail(val message: String) : LoginSideEffect
    data object KakaoLogin : LoginSideEffect
    data class NavigateToMain(val isNew: Boolean) : LoginSideEffect
}