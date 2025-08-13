package com.yapp.fitrun.core.data.mapper

import com.yapp.fitrun.core.domain.entity.AudioEntity
import com.yapp.fitrun.core.domain.entity.GoalEntity
import com.yapp.fitrun.core.domain.entity.HomeResultEntity
import com.yapp.fitrun.core.domain.entity.LocationEntity
import com.yapp.fitrun.core.domain.entity.LoginResultEntity
import com.yapp.fitrun.core.domain.entity.OnBoardingAnswers
import com.yapp.fitrun.core.domain.entity.OnBoardingEntity
import com.yapp.fitrun.core.domain.entity.RecordDataEntity
import com.yapp.fitrun.core.domain.entity.RecordDetailEntity
import com.yapp.fitrun.core.domain.entity.RecordDetailRunningPointEntity
import com.yapp.fitrun.core.domain.entity.RecordDetailSegmentsEntity
import com.yapp.fitrun.core.domain.entity.RecordEntity
import com.yapp.fitrun.core.domain.entity.RecordListEntity
import com.yapp.fitrun.core.domain.entity.RunnerEntity
import com.yapp.fitrun.core.domain.entity.TokenEntity
import com.yapp.fitrun.core.domain.entity.UserEntity
import com.yapp.fitrun.core.domain.entity.UserGoalEntity
import com.yapp.fitrun.core.domain.entity.UserInfoEntity
import com.yapp.fitrun.core.domain.entity.running.RunningCompleteEntity
import com.yapp.fitrun.core.domain.entity.running.RunningStartEntity
import com.yapp.fitrun.core.domain.entity.running.RunningUploadImageEntity
import com.yapp.fitrun.core.network.model.request.running.RunningPoint
import com.yapp.fitrun.core.network.model.response.AudioResponse
import com.yapp.fitrun.core.network.model.response.HomeResponse
import com.yapp.fitrun.core.network.model.response.LoginResponse
import com.yapp.fitrun.core.network.model.response.OnBoardingResponse
import com.yapp.fitrun.core.network.model.response.RecordDetailResponse
import com.yapp.fitrun.core.network.model.response.RecordListResponse
import com.yapp.fitrun.core.network.model.response.RecordResponse
import com.yapp.fitrun.core.network.model.response.RunnerResponse
import com.yapp.fitrun.core.network.model.response.TokenResponse
import com.yapp.fitrun.core.network.model.response.UserGoalResponse
import com.yapp.fitrun.core.network.model.response.UserInfoResponse
import com.yapp.fitrun.core.network.model.response.UserResponse
import com.yapp.fitrun.core.network.model.response.goal.GoalResponse
import com.yapp.fitrun.core.network.model.response.running.RunningCompleteResponse
import com.yapp.fitrun.core.network.model.response.running.RunningPointDto
import com.yapp.fitrun.core.network.model.response.running.RunningStartResponse
import com.yapp.fitrun.core.network.model.response.running.RunningUploadImageResponse
import com.yapp.fitrun.core.room.model.Location

internal fun AudioResponse.toEntity(): AudioEntity {
    return AudioEntity(
        audioData = result,
    )
}

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
        runnerType = runnerType,
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
        distanceMeterGoal = distanceMeterGoal,
        timeGoal = timeGoal,
        runnerType = runnerType,
    )

internal fun RecordListResponse.toEntity() =
    RecordListEntity(
        userId = userId,
        records = records.map {
            RecordDataEntity(
                it.recordId,
                it.userId,
                it.startAt,
                it.averagePace,
                it.totalDistance,
                it.totalTime,
                it.imageUrl,
            )
        },
        recordCount = recordCount,
        totalDistance = totalDistance,
        totalTime = totalTime,
        totalCalories = totalCalories,
        averagePace = averagePace,
        timeGoalAchievedCount = timeGoalAchievedCount,
        distanceGoalAchievedCount = distanceGoalAchievedCount,
    )

internal fun RecordDetailResponse.toEntity() =
    RecordDetailEntity(
        userId = userId,
        title = title,
        recordId = recordId,
        runningPoints = runningPoints.map { RecordDetailRunningPointEntity(it.lon, it.lat) },
        totalTime = totalTime,
        totalDistance = totalDistance,
        averagePace = averagePace,
        startAt = startAt,
        isTimeGoalAchieved = isTimeGoalAchieved,
        isPaceGoalAchieved = isPaceGoalAchieved,
        isDistanceGoalAchieved = isDistanceGoalAchieved,
        segments = segments.map {
            RecordDetailSegmentsEntity(
                it.orderNo,
                it.distanceMeter,
                it.averagePace,
            )
        },

    )

internal fun Location.toEntity() =
    LocationEntity(
        lat = lat,
        lng = lng,
    )

internal fun LocationEntity.toModel() =
    Location(
        lat = lat,
        lng = lng,
    )

internal fun UserInfoResponse.toEntity() =
    UserInfoEntity(
        user = user.toEntity(),
        goal = goal?.toEntity(),
    )

internal fun RunningStartResponse.toEntity() =
    RunningStartEntity(
        recordId = recordId,
    )

internal fun RunningCompleteResponse.toEntity() =
    RunningCompleteEntity(
        recordId = recordId,
        runningPoints = runningPointDtos.map { it.toEntity() },
        title = title,
        userId = userId,
    )

internal fun RunningPointDto.toEntity() =
    com.yapp.fitrun.core.domain.entity.running.RunningPoint(
        calories = calories,
        distance = distance,
        lat = lat,
        lon = lon,
        orderNo = orderNo,
        pace = pace,
        pointId = pointId,
        recordId = recordId,
        timeStamp = timeStamp,
        totalRunningDistance = totalRunningDistance,
        totalRunningTime = totalRunningTime,
        userId = userId,
    )

internal fun com.yapp.fitrun.core.domain.repository.RunningPoint.toModel() =
    RunningPoint(
        lat = lat,
        lon = lon,
        timeStamp = timeStamp,
        totalRunningTimeMills = totalRunningTimeMills,
    )

internal fun RunningUploadImageResponse.toEntity() =
    RunningUploadImageEntity(
        imageUrl = imageUrl,
        recordId = recordId,
        userId = userId,
    )
