package com.yapp.fitrun.core.network

import com.yapp.fitrun.core.network.api.GoalApiService
import com.yapp.fitrun.core.network.model.request.goal.DistanceGoalRequest
import com.yapp.fitrun.core.network.model.request.goal.PaceGoalRequest
import com.yapp.fitrun.core.network.model.request.goal.RunningPurposeRequest
import com.yapp.fitrun.core.network.model.request.goal.TimeGoalRequest
import com.yapp.fitrun.core.network.model.request.goal.WeeklyRunCountRequest
import com.yapp.fitrun.core.network.model.response.BaseResponse
import com.yapp.fitrun.core.network.model.response.goal.GoalResponse
import com.yapp.fitrun.core.network.model.response.goal.RecommendPaceResponse
import javax.inject.Inject

class GoalDataSourceImpl @Inject constructor(
    private val service: GoalApiService,
) : GoalDataSource {
    override suspend fun getGoal(): BaseResponse<GoalResponse> {
        return service.getGoal()
    }

    override suspend fun setDistanceGoal(distanceGoalRequest: DistanceGoalRequest): BaseResponse<GoalResponse> {
        return service.setDistanceGoal(distanceGoalRequest)
    }

    override suspend fun updateDistanceGoal(distanceGoalRequest: DistanceGoalRequest): BaseResponse<GoalResponse> {
        return service.updateDistanceGoal(distanceGoalRequest)
    }

    override suspend fun setPaceGoal(paceGoalRequest: PaceGoalRequest): BaseResponse<GoalResponse> {
        return service.setPaceGoal(paceGoalRequest)
    }

    override suspend fun updatePaceGoal(paceGoalRequest: PaceGoalRequest): BaseResponse<GoalResponse> {
        return service.updatePaceGoal(paceGoalRequest)
    }

    override suspend fun setTimeGoal(timeGoalRequest: TimeGoalRequest): BaseResponse<GoalResponse> {
        return service.setTimeGoal(timeGoalRequest)
    }

    override suspend fun updateTimeGoal(timeGoalRequest: TimeGoalRequest): BaseResponse<GoalResponse> {
        return service.updateTimeGoal(timeGoalRequest)
    }

    override suspend fun setWeeklyRunningCountGoal(weeklyRunCountRequest: WeeklyRunCountRequest): BaseResponse<GoalResponse> {
        return service.setWeeklyRunningCountGoal(weeklyRunCountRequest)
    }

    override suspend fun updateWeeklyRunCountGoal(weeklyRunCountRequest: WeeklyRunCountRequest): BaseResponse<GoalResponse> {
        return service.updateWeeklyRunningCountGoal(weeklyRunCountRequest)
    }

    override suspend fun setRunningPurpose(purpose: RunningPurposeRequest): BaseResponse<Unit> {
        return service.setRunningPurpose(purpose)
    }

    override suspend fun updateRunningPurpose(purpose: RunningPurposeRequest) {
        service.updateRunningPurpose(purpose)
    }

    override suspend fun getRecommendPace(): BaseResponse<RecommendPaceResponse> {
        return service.getRecommendPace()
    }
}
