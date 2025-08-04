package com.yapp.fitrun.feature.onboarding.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.yapp.fitrun.core.common.RunningPurpose
import com.yapp.fitrun.core.domain.entity.OnBoardingAnswers
import com.yapp.fitrun.core.domain.entity.OnBoardingEntity
import com.yapp.fitrun.core.domain.repository.GoalRepository
import com.yapp.fitrun.core.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

enum class Answers { A, B, C }

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val goalRepository: GoalRepository,
) : ViewModel(), ContainerHost<OnBoardingState, OnBoardingSideEffect> {
    override val container = container<OnBoardingState, OnBoardingSideEffect>(OnBoardingState())

    private val onBoardingAnswersMap = HashMap<String, String>()
    private val answerList = listOf(
        "EXPLOSIVE_STRENGTH",
        "AGILITY",
        "COORDINATION",
        "BALANCE",
        "EXERCISE_EXPERIENCE",
        "RUNNING_EXPERIENCE",
        "RUNNING_ENDURANCE",
        "PACE_AWARENESS",
    )

    init {
        answerList.forEach { onBoardingAnswersMap[it] = Answers.A.name }
    }

    fun onClickOnBoardingFirstQuestion1(selected: Int) = intent {
        onBoardingAnswersMap[answerList[0]] = convertSelectedToAnswer(selected).name
        postSideEffect(OnBoardingSideEffect.NavigateToOnBoardingSecond)
    }

    fun onClickOnBoardingFirstQuestion2(selected: Int) = intent {
        reduce {
            state.copy(isSelectedOnBoardingFirstQ2State = true)
        }
        onBoardingAnswersMap[answerList[1]] = convertSelectedToAnswer(selected).name
    }

    fun onClickOnBoardingFirstQuestion3(selected: Int) = intent {
        reduce {
            state.copy(isSelectedOnBoardingFirstQ3State = true)
        }
        onBoardingAnswersMap[answerList[2]] = convertSelectedToAnswer(selected).name
    }

    fun onClickOnBoardingFirstQuestion4(selected: Int) = intent {
        reduce {
            state.copy(isSelectedOnBoardingFirstQ4State = true)
        }
        onBoardingAnswersMap[answerList[3]] = convertSelectedToAnswer(selected).name
    }

    fun onClickOnBoardingSecondQuestion1(selected: Int) = intent {
        onBoardingAnswersMap[answerList[4]] = convertSelectedToAnswer(selected).name
        postSideEffect(OnBoardingSideEffect.NavigateToOnBoardingThird)
    }

    fun onClickOnBoardingSecondQuestion2(selected: Int) = intent {
        reduce {
            state.copy(isSelectedOnBoardingSecondQ2State = true)
        }
        onBoardingAnswersMap[answerList[5]] = convertSelectedToAnswer(selected).name
    }

    fun onClickOnBoardingThirdQuestion1(selected: Int) = intent {
        onBoardingAnswersMap[answerList[6]] = convertSelectedToAnswer(selected).name
        userRepository.setOnBoardingInfo(
            onBoardingEntity = OnBoardingEntity(
                answers = onBoardingAnswersMap.map { OnBoardingAnswers(it.key, it.value) },
            ),
        )
        postSideEffect(OnBoardingSideEffect.NavigateToOnBoardingFourth)
    }

    fun onClickOnBoardingThirdQuestion2(selected: Int) = intent {
        reduce {
            state.copy(isSelectedOnBoardingThirdQ2State = true)
        }
        onBoardingAnswersMap[answerList[7]] = convertSelectedToAnswer(selected).name
    }

    fun onClickOnBoardingFourth(selected: Int) = intent {
        goalRepository.setRunningPurpose(convertSelectedToPurpose(selected).purpose)
            .onSuccess {
                reduce {
                    state.copy(isLoading = true)
                }
                userRepository.getUserRunnerType()
                    .onSuccess { response ->
                        reduce { state.copy(isLoading = false, runnerTypeResult = response.runnerType) }
                    }
                    .onFailure { e ->
                        reduce { state.copy(isLoading = false) }
                        Log.e(this@OnBoardingViewModel.javaClass.name, "getRunnerType: " + e.message.toString())
                    }
            }
        postSideEffect(OnBoardingSideEffect.NavigateToOnBoardingResult)
    }

    private fun convertSelectedToPurpose(selected: Int): RunningPurpose {
        return when (selected) {
            0 -> {
                RunningPurpose.A
            }
            1 -> {
                RunningPurpose.B
            }
            2 -> {
                RunningPurpose.C
            }
            3 -> {
                RunningPurpose.D
            }
            else -> RunningPurpose.A
        }
    }

    private fun convertSelectedToAnswer(selected: Int): Answers {
        return when (selected) {
            0 -> {
                Answers.A
            }
            1 -> {
                Answers.B
            }
            2 -> {
                Answers.C
            }
            else -> Answers.A
        }
    }
}
