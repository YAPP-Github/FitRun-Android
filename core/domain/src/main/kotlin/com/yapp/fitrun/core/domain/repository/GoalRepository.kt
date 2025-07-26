package com.yapp.fitrun.core.domain.repository

import com.yapp.fitrun.core.domain.entity.GoalEntity

interface GoalRepository {
    // Goal 조회
    suspend fun getGoal(): Result<GoalEntity>

    // Distance Goal
    suspend fun setDistanceGoal(distance: Double): Result<GoalEntity>
    suspend fun updateDistanceGoal(distance: Double): Result<GoalEntity>

    // Pace Goal
    suspend fun setPaceGoal(pace: Int): Result<GoalEntity>
    suspend fun updatePaceGoal(pace: Int): Result<GoalEntity>

    // Time Goal
    suspend fun setTimeGoal(time: Int): Result<GoalEntity>
    suspend fun updateTimeGoal(time: Int): Result<GoalEntity>

    // Weekly Running Count Goal
    suspend fun setWeeklyRunningCountGoal(count: Int, isRemindEnabled: Boolean): Result<GoalEntity>
    suspend fun updateWeeklyRunningCountGoal(count: Int, isRemindEnabled: Boolean): Result<GoalEntity>

    // Running Purpose
    suspend fun setRunningPurpose(purpose: String): Result<Unit>
    suspend fun updateRunningPurpose(purpose: String): Result<Unit>

    // Recommend Pace
    suspend fun getRecommendPace(): Result<Int>
}
