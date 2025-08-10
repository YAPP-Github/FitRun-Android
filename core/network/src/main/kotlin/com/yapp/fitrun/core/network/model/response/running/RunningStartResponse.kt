package com.yapp.fitrun.core.network.model.response.running

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RunningStartResponse(
    @SerialName("recordId")
    val recordId: Int,
)
