package com.yapp.fitrun.core.network.model.response.running

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RunningUploadImageResponse(
    @SerialName("imageUrl")
    val imageUrl: String,
    @SerialName("recordId")
    val recordId: Int,
    @SerialName("userId")
    val userId: Int
)
