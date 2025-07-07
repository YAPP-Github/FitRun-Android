package com.yapp.fitrun.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String?,
    @SerialName("timeStamp")
    val timeStamp: String
)