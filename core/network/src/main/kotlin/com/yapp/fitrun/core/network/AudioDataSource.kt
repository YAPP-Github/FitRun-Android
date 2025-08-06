package com.yapp.fitrun.core.network

interface AudioDataSource {
    suspend fun getAudioCoach(): ByteArray
    suspend fun getRunningInfo(paceMills: String): ByteArray
    suspend fun getDistanceFeedback(type: String): ByteArray
    suspend fun getPaceFeedback(type: String): ByteArray
    suspend fun getTimeFeedback(type: String): ByteArray
}
