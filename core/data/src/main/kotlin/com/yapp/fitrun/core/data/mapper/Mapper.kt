package com.yapp.fitrun.core.data.mapper

import com.yapp.fitrun.core.domain.entity.GoalEntity
import com.yapp.fitrun.core.domain.entity.HomeResultEntity
import com.yapp.fitrun.core.domain.entity.LoginResultEntity
import com.yapp.fitrun.core.domain.entity.OnBoardingAnswers
import com.yapp.fitrun.core.domain.entity.OnBoardingEntity
import com.yapp.fitrun.core.domain.entity.RecordEntity
import com.yapp.fitrun.core.domain.entity.RunnerEntity
import com.yapp.fitrun.core.domain.entity.TokenEntity
import com.yapp.fitrun.core.domain.entity.UserEntity
import com.yapp.fitrun.core.domain.entity.UserGoalEntity
import com.yapp.fitrun.core.network.model.response.HomeResponse
import com.yapp.fitrun.core.network.model.response.LoginResponse
import com.yapp.fitrun.core.network.model.response.OnBoardingResponse
import com.yapp.fitrun.core.network.model.response.RecordResponse
import com.yapp.fitrun.core.network.model.response.RunnerResponse
import com.yapp.fitrun.core.network.model.response.TokenResponse
import com.yapp.fitrun.core.network.model.response.UserGoalResponse
import com.yapp.fitrun.core.network.model.response.UserResponse
import com.yapp.fitrun.core.network.model.response.goal.GoalResponse

// Network → Domain 변환
internal fun LoginResponse.toEntity() =
    LoginResultEntity(
        tokenEntity = TokenEntity(
            refreshToken = tokenResponse.refreshToken,
            accessToken = tokenResponse.accessToken,
        ),
        userEntity = user.toEntity(),
        isNewUser = isNew,
    )

internal fun TokenResponse.toEntity() =
    TokenEntity(
        refreshToken = refreshToken,
        accessToken = accessToken,
    )

internal fun UserResponse.toEntity() =
    UserEntity(
        id = id,
        nickname = nickname,
        email = email,
        provider = provider,
    )

internal fun OnBoardingResponse.toEntity() =
    OnBoardingEntity(
        answers = answerList.map { OnBoardingAnswers(it.questionType, it.answer) },
    )

internal fun RunnerResponse.toEntity() =
    RunnerEntity(
        userId = userId,
        runnerType = runnerType,
    )

internal fun RecordResponse.toEntity() =
    RecordEntity(
        recentDistanceMeter = recentDistanceMeter,
        recentPace = recentPace,
        recentTime = recentTime,
        thisWeekRunningCount = thisWeekRunningCount,
        totalDistance = totalDistance,
    )

internal fun UserGoalResponse.toEntity() =
    UserGoalEntity(
        distanceMeterGoal = distanceMeterGoal,
        paceGoal = paceGoal,
        runningPurpose = runningPurpose,
        timeGoal = timeGoal,
        weeklyRunningCount = weeklyRunningCount,
    )

internal fun HomeResponse.toEntity() =
    HomeResultEntity(
        userEntity = user.toEntity(),
        recordEntity = recordResponse.toEntity(),
        userGoalEntity = userGoalResponse.toEntity(),
    )

internal fun GoalResponse.toEntity() =
    GoalEntity(
        goalId = goalId,
        userId = userId,
        runningPurpose = runningPurpose,
        weeklyRunCount = weeklyRunCount,
        paceGoal = paceGoal,
        distanceMeter = distanceMeter,
        timeGoal = timeGoal,
    )
