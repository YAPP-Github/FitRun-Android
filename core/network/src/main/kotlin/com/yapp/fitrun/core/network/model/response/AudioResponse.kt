package com.yapp.fitrun.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AudioResponse(
    @SerialName("result")
    val result: ByteArray,
)
