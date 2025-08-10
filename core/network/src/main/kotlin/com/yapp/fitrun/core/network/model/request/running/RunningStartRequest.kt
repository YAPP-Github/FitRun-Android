package com.yapp.fitrun.core.network.model.request.running

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RunningStartRequest(
    @SerialName("lat")
    val lat: Double,
    @SerialName("lon")
    val lon: Double,
    @SerialName("timeStamp")
    val timeStamp: String,
)

