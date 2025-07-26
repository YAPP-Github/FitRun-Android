package com.yapp.fitrun.core.domain.entity

data class GoalEntity(
    val goalId: Long,
    val userId: Long,
    val runningPurpose: String,
    val weeklyRunCount: Int? = null,
    val paceGoal: Int? = null,
    val distanceMeter: Double? = null,
    val timeGoal: Int? = null,
)
