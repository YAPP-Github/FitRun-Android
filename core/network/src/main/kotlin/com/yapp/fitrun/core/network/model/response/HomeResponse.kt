package com.yapp.fitrun.core.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeResponse(
    @SerialName("record")
    val recordResponse: RecordResponse,
    @SerialName("user")
    val user: UserResponse,
    @SerialName("userGoal")
    val userGoalResponse: UserGoalResponse,
)

@Serializable
data class RecordResponse(
    @SerialName("recentDistanceMeter")
    val recentDistanceMeter: Double? = null,
    @SerialName("recentPace")
    val recentPace: Int? = null,
    @SerialName("recentTime")
    val recentTime: Int? = null,
    @SerialName("thisWeekRunningCount")
    val thisWeekRunningCount: Int,
    @SerialName("totalDistance")
    val totalDistance: Double,
)

@Serializable
data class UserGoalResponse(
    @SerialName("distanceMeterGoal")
    val distanceMeterGoal: Double? = null,
    @SerialName("paceGoal")
    val paceGoal: Int,
    @SerialName("runningPurpose")
    val runningPurpose: String,
    @SerialName("timeGoal")
    val timeGoal: Int? = null,
    @SerialName("weeklyRunningCount")
    val weeklyRunningCount: Int? = null,
)
