package com.yapp.fitrun.core.network.model.response.goal

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoalResponse(
    @SerialName("goalId")
    val goalId: Long,
    @SerialName("userId")
    val userId: Long,
    @SerialName("runningPurpose")
    val runningPurpose: String,
    @SerialName("weeklyRunningCount")
    val weeklyRunCount: Int? = null,
    @SerialName("paceGoal")
    val paceGoal: Int? = null,
    @SerialName("distanceMeter")
    val distanceMeter: Double? = null,
    @SerialName("timeGoal")
    val timeGoal: Int? = null,
)