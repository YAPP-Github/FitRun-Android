package com.yapp.fitrun.feature.onboarding.viewmodel

data class OnBoardingState(
    val isLoading: Boolean = false,

    val isSelectedOnBoardingFirstQ2State: Boolean = false,
    val isSelectedOnBoardingFirstQ3State: Boolean = false,
    val isSelectedOnBoardingFirstQ4State: Boolean = false,

    val isSelectedOnBoardingSecondQ2State: Boolean = false,

    val isSelectedOnBoardingThirdQ2State: Boolean = false,

    val runnerTypeResult: String = ""
)