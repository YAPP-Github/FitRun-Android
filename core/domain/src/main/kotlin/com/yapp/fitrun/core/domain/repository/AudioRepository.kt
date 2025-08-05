package com.yapp.fitrun.core.domain.repository

import com.yapp.fitrun.core.domain.entity.AudioEntity

interface AudioRepository {
    suspend fun getCoach(): Result<AudioEntity>
    suspend fun getRunningInfo(paceMills: String): Result<AudioEntity>
    suspend fun getDistanceFeedback(type: String): Result<AudioEntity>
    suspend fun getPaceFeedback(type: String): Result<AudioEntity>
    suspend fun getTimeFeedback(type: String): Result<AudioEntity>
}
