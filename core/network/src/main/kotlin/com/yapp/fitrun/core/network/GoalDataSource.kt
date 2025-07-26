package com.yapp.fitrun.core.network

import com.yapp.fitrun.core.network.model.request.goal.DistanceGoalRequest
import com.yapp.fitrun.core.network.model.request.goal.PaceGoalRequest
import com.yapp.fitrun.core.network.model.request.goal.RunningPurposeRequest
import com.yapp.fitrun.core.network.model.request.goal.TimeGoalRequest
import com.yapp.fitrun.core.network.model.request.goal.WeeklyRunCountRequest
import com.yapp.fitrun.core.network.model.response.BaseResponse
import com.yapp.fitrun.core.network.model.response.goal.GoalResponse
import com.yapp.fitrun.core.network.model.response.goal.RecommendPaceResponse

interface GoalDataSource {
    suspend fun getGoal(): BaseResponse<GoalResponse>
    suspend fun setDistanceGoal(distanceGoalRequest: DistanceGoalRequest): BaseResponse<GoalResponse>
    suspend fun updateDistanceGoal(distanceGoalRequest: DistanceGoalRequest): BaseResponse<GoalResponse>
    suspend fun setPaceGoal(paceGoalRequest: PaceGoalRequest): BaseResponse<GoalResponse>
    suspend fun updatePaceGoal(paceGoalRequest: PaceGoalRequest): BaseResponse<GoalResponse>
    suspend fun setTimeGoal(timeGoalRequest: TimeGoalRequest): BaseResponse<GoalResponse>
    suspend fun updateTimeGoal(timeGoalRequest: TimeGoalRequest): BaseResponse<GoalResponse>
    suspend fun setWeeklyRunningCountGoal(weeklyRunCountRequest: WeeklyRunCountRequest): BaseResponse<GoalResponse>
    suspend fun updateWeeklyRunCountGoal(weeklyRunCountRequest: WeeklyRunCountRequest): BaseResponse<GoalResponse>
    suspend fun setRunningPurpose(purpose: RunningPurposeRequest): BaseResponse<Unit>
    suspend fun updateRunningPurpose(purpose: RunningPurposeRequest)
    suspend fun getRecommendPace(): BaseResponse<RecommendPaceResponse>
}
