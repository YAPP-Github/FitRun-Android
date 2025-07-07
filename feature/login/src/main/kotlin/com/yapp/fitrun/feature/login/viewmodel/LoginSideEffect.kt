package com.yapp.fitrun.feature.login.viewmodel


// Side Effects
sealed interface LoginSideEffect {
    data class ShowError(val message: String) : LoginSideEffect
    data object KakaoLogin : LoginSideEffect
    data object LoginSuccess : LoginSideEffect
    data class LoginFail(val throwable: Throwable) : LoginSideEffect
}