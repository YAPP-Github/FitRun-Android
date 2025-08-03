package com.yapp.fitrun.core.network.model.response

import com.yapp.fitrun.core.network.model.response.goal.GoalResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    @SerialName("goal")
    val goal: GoalResponse? = null,
    @SerialName("user")
    val user: UserResponse,
)
