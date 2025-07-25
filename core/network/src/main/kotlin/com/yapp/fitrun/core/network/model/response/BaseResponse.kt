package com.yapp.fitrun.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("code")
    val code: String,
    @SerialName("result")
    val result: T,
    @SerialName("timeStamp")
    val timeStamp: String,
)
