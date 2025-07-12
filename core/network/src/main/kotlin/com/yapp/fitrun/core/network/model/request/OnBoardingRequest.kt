package com.yapp.fitrun.core.network.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnBoardingRequest(
    @SerialName("answers")
    val answers: List<OnBoardingAnswers>,
)

@Serializable
data class OnBoardingAnswers(
    @SerialName("questionType")
    val questionType: String,

    @SerialName("answer")
    val answer: String,
)