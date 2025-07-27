package com.yapp.fitrun.feature.running.runningonboarding.viewmodel

sealed interface RunningOnBoardingSideEffect {
    data object NavigateToRunningOnBoardingThird : RunningOnBoardingSideEffect
    data object NavigateToReady : RunningOnBoardingSideEffect
}
