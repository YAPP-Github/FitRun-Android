package com.yapp.fitrun.core.network.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RunnerRequest(
    @SerialName("runnerType")
    val runnerType: String,
)
