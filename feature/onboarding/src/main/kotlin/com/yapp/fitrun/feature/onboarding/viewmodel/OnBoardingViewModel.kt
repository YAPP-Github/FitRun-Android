package com.yapp.fitrun.feature.onboarding.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.yapp.fitrun.core.domain.entity.OnBoardingEntity
import com.yapp.fitrun.core.domain.repository.TokenRepository
import com.yapp.fitrun.core.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository,
): ViewModel(), ContainerHost<OnBoardingState, OnBoardingSideEffect> {
    override val container = container<OnBoardingState, OnBoardingSideEffect>(OnBoardingState())

    private val firstSelectedValue: List<String> = emptyList()
    private val secondSelectedValue: List<String> = emptyList()
    private val thirdSelectedValue: List<String> = emptyList()

    fun onClickOnBoardingFirst() = intent {
        val newCount: Int =  state.selectedOnBoardingFirstStateCount + 1
        reduce {
            state.copy(selectedOnBoardingFirstStateCount = newCount)
        }
        if (newCount >= 4) {
            postSideEffect(OnBoardingSideEffect.NavigateToOnBoardingSecond)
        }
    }

    fun onClickOnBoardingSecond() = intent {
        val newCount: Int =  state.selectedOnBoardingSecondStateCount + 1
        reduce {
            state.copy(selectedOnBoardingSecondStateCount = newCount)
        }
        if (newCount >= 2) {
            postSideEffect(OnBoardingSideEffect.NavigateToOnBoardingThird)
        }
    }

    fun onClickOnBoardingThird() = intent {
        val newCount: Int =  state.selectedOnBoardingThirdStateCount + 1
        reduce {
            state.copy(selectedOnBoardingThirdStateCount = newCount)
        }
        if (newCount >= 2) {
            postSideEffect(OnBoardingSideEffect.NavigateToOnBoardingFourth)
        }
    }

    fun setOnBoardingInfo() = intent {
        userRepository.setOnBoardingInfo(
            onBoardingEntity = OnBoardingEntity(
                answers =
            )
        )
    }

    fun getRunnerType() = intent {
        reduce {
            state.copy(isLoading = true)
        }
        userRepository.getUserRunnerType()
            .onSuccess { response ->
                reduce { state.copy(isLoading = false, runnerTypeResult = response.runnerType) }
            }
            .onFailure { e ->
                reduce { state.copy(isLoading = false) }
                Log.e(this@OnBoardingViewModel.javaClass.name, e.message.toString())
            }
    }
}