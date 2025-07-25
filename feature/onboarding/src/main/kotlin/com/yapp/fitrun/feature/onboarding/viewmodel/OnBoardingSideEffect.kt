package com.yapp.fitrun.feature.onboarding.viewmodel

sealed interface OnBoardingSideEffect {
    data object NavigateToOnBoardingSecond : OnBoardingSideEffect
    data object NavigateToOnBoardingThird : OnBoardingSideEffect
    data object NavigateToOnBoardingFourth : OnBoardingSideEffect
    data object NavigateToOnBoardingResult : OnBoardingSideEffect
}
