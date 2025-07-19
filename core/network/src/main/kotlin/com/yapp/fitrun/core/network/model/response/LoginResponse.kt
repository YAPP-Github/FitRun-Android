package com.yapp.fitrun.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("tokenResponse")
    val tokenResponse: TokenResponse,
    @SerialName("user")
    val user: UserResponse,
    @SerialName("isNew")
    val isNew: Boolean
)

@Serializable
data class TokenResponse(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String
)

@Serializable
data class UserResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("email")
    val email: String? = null,
    @SerialName("provider")
    val provider: String? = null
)
