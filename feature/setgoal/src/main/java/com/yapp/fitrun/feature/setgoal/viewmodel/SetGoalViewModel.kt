package com.yapp.fitrun.feature.setgoal.viewmodel

import androidx.lifecycle.ViewModel
import com.yapp.fitrun.core.domain.repository.GoalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SetGoalViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
) : ViewModel(), ContainerHost<SetGoalState, SetGoalSideEffect> {

    override val container: Container<SetGoalState, SetGoalSideEffect> = container(SetGoalState())

    fun showPaceWarning() = intent {
        reduce { state.copy(showPaceWarning = true) }
        delay(1500)
        reduce { state.copy(showPaceWarning = false) }
    }

    fun setPaceGoalOnBoarding() = intent {
        reduce { state.copy(setPaceOnBoardingSuccess = true) }
        delay(3000)
        reduce { state.copy(setPaceOnBoardingSuccess = false) }
        postSideEffect(SetGoalSideEffect.NavigateToRecordDetail(0))
    }

    fun setPaceGoal(pace: Int) = intent {
        reduce { state.copy(isLoading = true) }

        goalRepository.setPaceGoal(pace)
            .onSuccess { goalEntity ->
                reduce {
                    state.copy(
                        isLoading = false,
                        paceGoal = pace,
                        goalEntity = goalEntity,
                    )
                }
                postSideEffect(SetGoalSideEffect.ShowSuccessToast("페이스 목표가 설정되었습니다"))
            }
            .onFailure { error ->
                reduce { state.copy(isLoading = false) }
                postSideEffect(SetGoalSideEffect.ShowErrorToast(error.message ?: "페이스 설정 중 오류가 발생했습니다"))
            }
    }

    fun setWeeklyRunningCountGoal(count: Int, isRemindEnabled: Boolean) = intent {
        reduce { state.copy(isLoading = true) }

        goalRepository.setWeeklyRunningCountGoal(count, isRemindEnabled)
            .onSuccess { goalEntity ->
                reduce {
                    state.copy(
                        isLoading = false,
                        weeklyRunCount = count,
                        isRemindEnabled = isRemindEnabled,
                        goalEntity = goalEntity,
                    )
                }
                postSideEffect(SetGoalSideEffect.ShowSuccessToast("주간 러닝 목표가 설정되었습니다"))
            }
            .onFailure { error ->
                reduce { state.copy(isLoading = false) }
                postSideEffect(SetGoalSideEffect.ShowErrorToast(error.message ?: "러닝 횟수 설정 중 오류가 발생했습니다"))
            }
    }

    fun updatePaceGoal(pace: Int) = intent {
        reduce { state.copy(isLoading = true) }

        goalRepository.updatePaceGoal(pace)
            .onSuccess { goalEntity ->
                reduce {
                    state.copy(
                        isLoading = false,
                        paceGoal = pace,
                        goalEntity = goalEntity,
                    )
                }
                postSideEffect(SetGoalSideEffect.ShowSuccessToast("페이스 목표가 수정되었습니다"))
            }
            .onFailure { error ->
                reduce { state.copy(isLoading = false) }
                postSideEffect(SetGoalSideEffect.ShowErrorToast(error.message ?: "페이스 수정 중 오류가 발생했습니다"))
            }
    }

    fun updateWeeklyRunningCountGoal(count: Int, isRemindEnabled: Boolean) = intent {
        reduce { state.copy(isLoading = true) }

        goalRepository.updateWeeklyRunningCountGoal(count, isRemindEnabled)
            .onSuccess { goalEntity ->
                reduce {
                    state.copy(
                        isLoading = false,
                        weeklyRunCount = count,
                        isRemindEnabled = isRemindEnabled,
                        goalEntity = goalEntity,
                    )
                }
                postSideEffect(SetGoalSideEffect.ShowSuccessToast("주간 러닝 목표가 수정되었습니다"))
            }
            .onFailure { error ->
                reduce { state.copy(isLoading = false) }
                postSideEffect(SetGoalSideEffect.ShowErrorToast(error.message ?: "러닝 횟수 수정 중 오류가 발생했습니다"))
            }
    }

    fun getGoal() = intent {
        reduce { state.copy(isLoading = true) }

        goalRepository.getGoal()
            .onSuccess { goalEntity ->
                reduce {
                    state.copy(
                        isLoading = false,
                        goalEntity = goalEntity,
                        paceGoal = goalEntity.paceGoal,
                        weeklyRunCount = goalEntity.weeklyRunCount,
                    )
                }
            }
            .onFailure { error ->
                reduce { state.copy(isLoading = false) }
                postSideEffect(SetGoalSideEffect.ShowErrorToast(error.message ?: "목표 정보를 불러오는 중 오류가 발생했습니다"))
            }
    }

    fun getRecommendPace() = intent {
        reduce { state.copy(isLoadingRecommendPace = true) }

        goalRepository.getRecommendPace()
            .onSuccess { recommendPace ->
                reduce {
                    state.copy(
                        isLoadingRecommendPace = false,
                        recommendPace = recommendPace,
                    )
                }
            }
            .onFailure { error ->
                reduce { state.copy(isLoadingRecommendPace = false) }
                postSideEffect(SetGoalSideEffect.ShowErrorToast(error.message ?: "추천 페이스를 불러오는 중 오류가 발생했습니다"))
            }
    }

    fun setPaceInUI(pace: Int) = intent {
        reduce { state.copy(currentPaceInput = pace) }
    }

    fun setWeeklyRunCountInUI(count: Int) = intent {
        reduce { state.copy(currentRunCountInput = count) }
    }

    fun setRemindEnabledInUI(enabled: Boolean) = intent {
        reduce { state.copy(currentRemindEnabled = enabled) }
    }

    fun submitGoals() = intent {
        val paceSeconds = state.currentPaceInput
        val runCount = state.currentRunCountInput
        val remindEnabled = state.currentRemindEnabled

        if (paceSeconds == null || runCount == null) {
            postSideEffect(SetGoalSideEffect.ShowErrorToast("모든 목표를 설정해주세요"))
            return@intent
        }

        reduce { state.copy(isLoading = true) }

        // 기존 목표가 있는지 확인
        if (state.goalEntity != null) {
            // 업데이트
            val paceResult = goalRepository.updatePaceGoal(paceSeconds)
            val countResult = goalRepository.updateWeeklyRunningCountGoal(runCount, remindEnabled)

            if (paceResult.isSuccess && countResult.isSuccess) {
                reduce {
                    state.copy(
                        isLoading = false,
                        paceGoal = paceSeconds,
                        weeklyRunCount = runCount,
                        isRemindEnabled = remindEnabled,
                    )
                }
                postSideEffect(SetGoalSideEffect.ShowCompleteLottie)
            } else {
                reduce { state.copy(isLoading = false) }
                postSideEffect(SetGoalSideEffect.ShowErrorToast("목표 설정 중 오류가 발생했습니다"))
            }
        } else {
            // 새로 설정
            val paceResult = goalRepository.setPaceGoal(paceSeconds)
            val countResult = goalRepository.setWeeklyRunningCountGoal(runCount, remindEnabled)

            if (paceResult.isSuccess && countResult.isSuccess) {
                reduce {
                    state.copy(
                        isLoading = false,
                        paceGoal = paceSeconds,
                        weeklyRunCount = runCount,
                        isRemindEnabled = remindEnabled,
                    )
                }
                postSideEffect(SetGoalSideEffect.ShowCompleteLottie)
            } else {
                reduce { state.copy(isLoading = false) }
                postSideEffect(SetGoalSideEffect.ShowErrorToast("목표 설정 중 오류가 발생했습니다"))
            }
        }
    }
}
