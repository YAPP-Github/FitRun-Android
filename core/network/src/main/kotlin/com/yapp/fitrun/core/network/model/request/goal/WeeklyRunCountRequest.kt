package com.yapp.fitrun.core.network.model.request.goal

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeeklyRunCountRequest(
    @SerialName("count")
    val count: Int,
    @SerialName("remindAlert")
    val remindAlert: Boolean,
)
