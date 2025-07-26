package com.yapp.fitrun.core.network.api

import com.yapp.fitrun.core.network.model.request.goal.DistanceGoalRequest
import com.yapp.fitrun.core.network.model.request.goal.PaceGoalRequest
import com.yapp.fitrun.core.network.model.request.goal.RunningPurposeRequest
import com.yapp.fitrun.core.network.model.request.goal.TimeGoalRequest
import com.yapp.fitrun.core.network.model.request.goal.WeeklyRunCountRequest
import com.yapp.fitrun.core.network.model.response.BaseResponse
import com.yapp.fitrun.core.network.model.response.goal.GoalResponse
import com.yapp.fitrun.core.network.model.response.goal.RecommendPaceResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface GoalApiService {

    @GET("/api/v1/users/goals")
    suspend fun getGoal(): BaseResponse<GoalResponse>

    // Distance Goal
    @POST("/api/v1/users/goals/distance")
    suspend fun setDistanceGoal(@Body distanceGoalRequest: DistanceGoalRequest): BaseResponse<GoalResponse>

    @PATCH("/api/v1/users/goals/distance")
    suspend fun updateDistanceGoal(@Body distanceGoalRequest: DistanceGoalRequest): BaseResponse<GoalResponse>

    // Pace Goal
    @POST("/api/v1/users/goals/pace")
    suspend fun setPaceGoal(@Body paceGoalRequest: PaceGoalRequest): BaseResponse<GoalResponse>

    @PATCH("/api/v1/users/goals/pace")
    suspend fun updatePaceGoal(@Body paceGoalRequest: PaceGoalRequest): BaseResponse<GoalResponse>

    // Time Goal
    @POST("/api/v1/users/goals/time")
    suspend fun setTimeGoal(@Body timeGoalRequest: TimeGoalRequest): BaseResponse<GoalResponse>

    @PATCH("/api/v1/users/goals/time")
    suspend fun updateTimeGoal(@Body timeGoalRequest: TimeGoalRequest): BaseResponse<GoalResponse>

    // Weekly Running Count Goal
    @POST("/api/v1/users/goals/weekly-run-count")
    suspend fun setWeeklyRunningCountGoal(@Body weeklyRunCountRequest: WeeklyRunCountRequest): BaseResponse<GoalResponse>

    @PATCH("/api/v1/users/goals/weekly-run-count")
    suspend fun updateWeeklyRunningCountGoal(@Body weeklyRunCountRequest: WeeklyRunCountRequest): BaseResponse<GoalResponse>

    // Running Purpose
    @POST("/api/v1/users/goals/purpose")
    suspend fun setRunningPurpose(@Body runningPurposeRequest: RunningPurposeRequest): BaseResponse<Unit>

    @PATCH("/api/v1/users/goals/purpose")
    suspend fun updateRunningPurpose(@Body runningPurposeRequest: RunningPurposeRequest)

    // Recommend Pace
    @GET("/api/v1/users/goals/pace/recommend")
    suspend fun getRecommendPace(): BaseResponse<RecommendPaceResponse>
}
