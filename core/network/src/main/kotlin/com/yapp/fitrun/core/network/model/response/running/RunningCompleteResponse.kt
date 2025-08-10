package com.yapp.fitrun.core.network.model.response.running

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RunningCompleteResponse(
    @SerialName("recordId")
    val recordId: Int,
    @SerialName("runningPoints")
    val runningPointDtos: List<RunningPointDto>,
    @SerialName("title")
    val title: String,
    @SerialName("userId")
    val userId: Int,
)

@Serializable
data class RunningPointDto(
    val calories: Int,
    val distance: Double,
    val lat: Double,
    val lon: Double,
    val orderNo: Int,
    val pace: Int,
    val pointId: Int,
    val recordId: Int,
    val timeStamp: String,
    val totalRunningDistance: Double,
    val totalRunningTime: Int,
    val userId: Int,
)
