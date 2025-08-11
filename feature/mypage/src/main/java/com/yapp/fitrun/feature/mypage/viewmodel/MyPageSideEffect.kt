package com.yapp.fitrun.feature.mypage.viewmodel

sealed interface MyPageSideEffect {
    data object NavigateToLogin: MyPageSideEffect
}
