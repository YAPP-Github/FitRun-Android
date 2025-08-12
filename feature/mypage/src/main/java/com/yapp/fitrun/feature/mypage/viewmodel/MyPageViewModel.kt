package com.yapp.fitrun.feature.mypage.viewmodel

import android.util.Log
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import com.yapp.fitrun.core.common.RunningPurpose
import com.yapp.fitrun.core.common.convertRunnerType
import com.yapp.fitrun.core.common.convertRunningPurpose
import com.yapp.fitrun.core.common.convertTimeToPace
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.core.domain.entity.RunnerEntity
import com.yapp.fitrun.core.domain.repository.GoalRepository
import com.yapp.fitrun.core.domain.repository.TokenRepository
import com.yapp.fitrun.core.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository,
    private val goalRepository: GoalRepository,
) : ViewModel(), ContainerHost<MyPageState, MyPageSideEffect> {
    override val container = container<MyPageState, MyPageSideEffect>(MyPageState())

    fun getUserInfo() = intent {
        reduce { state.copy(isLoading = true) }

        userRepository.getUserInfo().onSuccess { response ->
            reduce {
                state.copy(
                    isLoading = false,
                    userNickName = response.user.nickname,
                    userEmail = response.user.email ?: "",
                )
            }

            response.goal?.let {
                val purpose = response.goal?.runningPurpose?.let {
                    convertRunningPurpose(it)
                } ?: ""
                reduce {
                    state.copy(
                        isLoading = false,
                        userLevel = convertRunnerType(response.user.runnerType ?: ""),
                        userLevelImageId = getUserLevelImageId(response.user.runnerType ?: ""),
                        userNickName = response.user.nickname,
                        userEmail = response.user.email ?: "",
                        userRunningPurpose = purpose,
                        userRunningPurposeImageId = getUserRunningPurposeImageId(purpose),
                        userGoalDistance = response.goal?.distanceMeterGoal?.let {
                            "${(it.div(1000f))}"
                        } ?: "",
                        userGoalTime = response.goal?.timeGoal?.let { "${it / 60000}" }
                            ?: "",
                        userGoalPace = response.goal?.paceGoal?.let { convertTimeToPace(it) }
                            ?: "",
                        userGoalFrequency = response.goal?.weeklyRunCount?.let { "주 ${it}회" }
                            ?: "",
                    )
                }
            }
        }.onFailure {
            reduce { state.copy(isLoading = false) }
            Log.e(
                this@MyPageViewModel.javaClass.name,
                "getUserInfo: " + it.message.toString(),
            )
        }
    }

    fun onClickDeleteAccount() = intent {
        userRepository.deleteAccount()
        tokenRepository.clear()
        postSideEffect(MyPageSideEffect.NavigateToLogin)
    }

    fun onClickLogout() = intent {
        tokenRepository.clear()
        postSideEffect(MyPageSideEffect.NavigateToLogin)
    }

    fun onClickChangeRunningPurpose(purpose: String) = intent {
        val param = when (purpose) {
            "다이어트" -> RunningPurpose.A.purpose
            "건강 관리" -> RunningPurpose.B.purpose
            "체력 증진" -> RunningPurpose.C.purpose
            "대회 준비" -> RunningPurpose.D.purpose
            else -> RunningPurpose.A.purpose

        }
        val imageId = getUserRunningPurposeImageId(purpose)
        goalRepository.updateRunningPurpose(param).onSuccess {
            reduce { state.copy(userRunningPurpose = purpose, userRunningPurposeImageId = imageId) }
        }
    }

    fun onClickChangeRunningLevel(level: Int) = intent {
        val param = when (level) {
            0 -> "BEGINNER"
            1 -> "INTERMEDIATE"
            2 -> "EXPERT"
            else -> "BEGINNER"
        }
        val imageId = getUserLevelImageId(param)
        userRepository.updateUserRunnerType(
            runnerTypeEntity = RunnerEntity(
                runnerType = param,
                userId = 0,
            )
        ).onSuccess { response ->
            val type = convertRunnerType(response.runnerType)
            reduce { state.copy(userLevel = type, userLevelImageId = imageId) }
        }
    }

    fun onClickChangeRunningTimeGoal(timeGoal: String) = intent {
        val param = timeGoal.toLong() * 60000
        goalRepository.updateTimeGoal(param).onSuccess {
            reduce { state.copy(userGoalTime = timeGoal) }
        }
    }

    fun onClickChangeRunningDistanceGoal(distanceGoal: String) = intent {
        val param = distanceGoal.toDouble() * 1000
        goalRepository.updateDistanceGoal(param).onSuccess {
            reduce { state.copy(userGoalDistance = distanceGoal) }
        }
    }

    fun onClickNotification() = intent {
        reduce { state.copy(isNotification = !state.isNotification) }
    }

    fun onClickAudioCoaching() = intent {
        reduce { state.copy(isAudioCoaching = !state.isAudioCoaching) }
    }

    fun onClickAudioFeedback() = intent {
        reduce { state.copy(isAudioFeedback = !state.isAudioFeedback) }
    }

    private fun getUserRunningPurposeImageId(purpose: String): Int {
        return when (purpose) {
            "다이어트" -> R.drawable.img_fire
            "건강 관리" -> R.drawable.img_heart
            "체력 증진" -> R.drawable.img_battery
            "대회 준비" -> R.drawable.img_medal
            else -> R.drawable.img_fire
        }
    }

    private fun getUserLevelImageId(level: String): Int {
        return when (level) {
            "BEGINNER" -> R.drawable.img_chicken
            "INTERMEDIATE" -> R.drawable.img_stopwatch
            "EXPERT" -> R.drawable.img_rocket
            else -> R.drawable.img_chicken
        }
    }
}
