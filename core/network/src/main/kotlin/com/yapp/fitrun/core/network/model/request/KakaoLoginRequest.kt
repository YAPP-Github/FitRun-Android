package com.yapp.fitrun.core.network.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KakaoLoginRequest(
    @SerialName("idToken")
    val idToken: String,
    @SerialName("nonce")
    val nonce: String? = null,
    @SerialName("name")
    val name: String? = null  // nullable로 변경
)
