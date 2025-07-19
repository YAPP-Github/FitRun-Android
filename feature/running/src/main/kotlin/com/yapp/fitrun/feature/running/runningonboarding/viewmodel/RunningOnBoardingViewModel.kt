package com.yapp.fitrun.feature.running.runningonboarding.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RunningOnBoardingViewModel @Inject constructor(
): ViewModel(), ContainerHost<RunningOnBoardingState, RunningOnBoardingSideEffect> {
    override val container =
        container<RunningOnBoardingState, RunningOnBoardingSideEffect>(RunningOnBoardingState())


}
