package com.yapp.fitrun.feature.running.runningonboarding.viewmodel

sealed interface RunningOnBoardingSideEffect {
    data object NavigateToOnBoardingSecond : RunningOnBoardingSideEffect
}
