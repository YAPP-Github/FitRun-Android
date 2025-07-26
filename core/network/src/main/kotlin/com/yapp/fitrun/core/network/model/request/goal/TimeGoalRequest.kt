package com.yapp.fitrun.core.network.model.request.goal

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TimeGoalRequest(
    @SerialName("time")
    val time: Int,
)
