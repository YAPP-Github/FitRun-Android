package com.yapp.fitrun.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("userId")
    val id: Long,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("email")
    val email: String? = null,
    @SerialName("provider")
    val provider: String? = null,
    @SerialName("runnerType")
    val runnerType: String? = null,
)
