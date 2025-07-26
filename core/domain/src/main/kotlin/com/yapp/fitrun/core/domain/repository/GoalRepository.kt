package com.yapp.fitrun.core.domain.repository

interface GoalRepository {
    suspend fun setRunningPurpose(purpose: String): Result<Unit>
    suspend fun updateRunningPurpose(purpose: String)
}
