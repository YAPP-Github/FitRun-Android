package com.yapp.fitrun.core.domain.entity

data class OnBoardingEntity(
    val answers: List<OnBoardingAnswers>,
)

data class OnBoardingAnswers(
    val questionType: String,
    val answer: String,
)
