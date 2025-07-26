package com.yapp.fitrun.core.network.model.request.goal

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaceGoalRequest(
    @SerialName("pace")
    val pace: Int,
)
