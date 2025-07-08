package com.yapp.fitrun.feature.onboarding.viewmodel

sealed interface OnBoardingSideEffect {
    data object NavigateToHome: OnBoardingSideEffect
    data object NavigateToRoutine: OnBoardingSideEffect
}