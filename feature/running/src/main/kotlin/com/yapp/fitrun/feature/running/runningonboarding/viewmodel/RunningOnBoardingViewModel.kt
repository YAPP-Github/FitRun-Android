package com.yapp.fitrun.feature.running.runningonboarding.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RunningOnBoardingViewModel @Inject constructor() : ViewModel(),
    ContainerHost<RunningOnBoardingState, RunningOnBoardingSideEffect> {
    override val container =
        container<RunningOnBoardingState, RunningOnBoardingSideEffect>(RunningOnBoardingState())

    fun onClickSetGoal(selected: Int) = intent {
        // 0: time, 1: km
        reduce {
            state.copy(
                isShowTimeGoal = (selected == 0),
                isShowDistanceGoal = (selected == 1),
            )
        }
        postSideEffect(RunningOnBoardingSideEffect.NavigateToRunningOnBoardingThird)
    }

    fun onClickSaveGoal() = intent {
        reduce { state.copy(isSetGoalSuccess = true) }
        delay(3000)
        reduce { state.copy(isSetGoalSuccess = false) }
        postSideEffect(RunningOnBoardingSideEffect.NavigateToReady)
    }
}
