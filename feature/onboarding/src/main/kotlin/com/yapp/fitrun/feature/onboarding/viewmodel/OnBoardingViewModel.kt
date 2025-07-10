package com.yapp.fitrun.feature.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

@HiltViewModel
class OnBoardingViewModel @Inject constructor(

): ViewModel(), ContainerHost<OnBoardingState, OnBoardingSideEffect> {
    override val container = container<OnBoardingState, OnBoardingSideEffect>(OnBoardingState())

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
}