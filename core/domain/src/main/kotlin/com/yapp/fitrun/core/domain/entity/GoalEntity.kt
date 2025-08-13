package com.yapp.fitrun.core.domain.entity

data class GoalEntity(
    val goalId: Long,
    val userId: Long,
    val runningPurpose: String,
    val weeklyRunCount: Int? = null,
    val paceGoal: Long? = null,
    val distanceMeterGoal: Double? = null,
    val timeGoal: Long? = null,
    val runnerType: String? = null,
)
