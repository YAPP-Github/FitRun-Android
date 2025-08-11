package com.yapp.fitrun.feature.mypage.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.yapp.fitrun.core.common.RunningPurpose
import com.yapp.fitrun.core.common.convertRunningPurpose
import com.yapp.fitrun.core.common.convertTimeToPace
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
        val noGoalMessage = "설정되지 않았어요"
        reduce { state.copy(isLoading = true) }

        userRepository.getUserInfo()
            .onSuccess { response ->
                reduce {
                    state.copy(
                        isLoading = false,
                        userNickName = response.user.nickname,
                        userEmail = response.user.email ?: "",
                    )
                }

                response.goal?.let {
                    reduce {
                        state.copy(
                            isLoading = false,
                            userNickName = response.user.nickname,
                            userEmail = response.user.email ?: "",
                            userRunningPurpose = response.goal?.runningPurpose?.let {
                                convertRunningPurpose(it)
                            } ?: noGoalMessage,
                            userGoalDistance = response.goal?.distanceMeterGoal?.let {
                                "${(it.div(1000f))}km"
                            } ?: noGoalMessage,
                            userGoalTime = response.goal?.timeGoal?.let { "${it / 60000}분" }
                                ?: noGoalMessage,
                            userGoalPace = response.goal?.paceGoal?.let { convertTimeToPace(it) }
                                ?: noGoalMessage,
                            userGoalFrequency = response.goal?.weeklyRunCount?.let { "주 ${it}회" }
                                ?: noGoalMessage,
                        )
                    }
                }
            }
            .onFailure {
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

    fun onClickChangeRunningPurpose(purpose: String) = intent {
        println(purpose)
        val param =
            when (purpose) {
                "다이어트" -> RunningPurpose.A.purpose
                "건강 관리" -> RunningPurpose.B.purpose
                "체력 증진" -> RunningPurpose.C.purpose
                "대회 준비" -> RunningPurpose.D.purpose
                else -> RunningPurpose.A.purpose

            }
        goalRepository.updateRunningPurpose(param).onSuccess {
            reduce { state.copy(userRunningPurpose = purpose) }
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
}
