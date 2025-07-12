package com.yapp.fitrun.core.network.model.response

import com.yapp.fitrun.core.network.model.request.OnBoardingAnswers
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnBoardingResponse(
    @SerialName("answerList")
    val answerList: List<OnBoardingAnswers>,
)
