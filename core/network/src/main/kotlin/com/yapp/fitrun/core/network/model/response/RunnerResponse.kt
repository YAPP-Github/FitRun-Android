package com.yapp.fitrun.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RunnerResponse(
    @SerialName("userId")
    val userId: Int,
    @SerialName("runnerType")
    val runnerType: String,
)
