package com.yapp.fitrun.feature.onboarding.viewmodel

data class OnBoardingState(
    val isLoading: Boolean = false,
    val selectedOnBoardingFirstStateCount: Int = 0,
    val selectedOnBoardingSecondStateCount: Int = 0,
    val selectedOnBoardingThirdStateCount: Int = 0,
)