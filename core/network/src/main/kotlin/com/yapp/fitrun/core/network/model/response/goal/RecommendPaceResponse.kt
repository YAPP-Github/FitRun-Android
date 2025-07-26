package com.yapp.fitrun.core.network.model.response.goal

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendPaceResponse(
    @SerialName("userId")
    val userId: Long,
    @SerialName("recommendPace")
    val recommendPace: Int
)
