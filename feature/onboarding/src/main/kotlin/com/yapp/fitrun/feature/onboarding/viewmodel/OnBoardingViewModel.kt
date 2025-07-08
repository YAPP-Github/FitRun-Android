package com.yapp.fitrun.feature.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container
import org.orbitmvi.orbit.syntax.simple.reduce

@HiltViewModel
class OnBoardingViewModel @Inject constructor(

): ViewModel(), ContainerHost<OnBoardingState, OnBoardingSideEffect> {
    override val container = container<OnBoardingState, OnBoardingSideEffect>(OnBoardingState())

    init {

    }

    fun onClickNavigateToHome() = intent {
        postSideEffect(OnBoardingSideEffect.NavigateToHome)
    }

    fun onClickNavigateToRoutine() = intent {
        postSideEffect(OnBoardingSideEffect.NavigateToRoutine)
    }
}