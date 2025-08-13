package com.yapp.fitrun.core.network.model.request.running

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RunningCompleteRequest(
    @SerialName("averagePace")
    val averagePace: Int,
    @SerialName("runningPoints")
    val runningPoints: List<RunningPoint>,
    @SerialName("startAt")
    val startAt: String,
    @SerialName("totalCalories")
    val totalCalories: Int,
    @SerialName("totalDistance")
    val totalDistance: Double,
    @SerialName("totalTime")
    val totalTime: Int,
)

@Serializable
data class RunningPoint(
    @SerialName("lat")
    val lat: Double,
    @SerialName("lon")
    val lon: Double,
    @SerialName("timeStamp")
    val timeStamp: String,
    @SerialName("totalRunningTimeMills")
    val totalRunningTimeMills: Int,
)
