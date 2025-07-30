package com.yapp.fitrun.feature.setgoal.viewmodel

sealed class SetGoalSideEffect {
    data class ShowSuccessToast(val message: String) : SetGoalSideEffect()
    data class ShowErrorToast(val message: String) : SetGoalSideEffect()
    object NavigateToComplete : SetGoalSideEffect()
    object ShowCompleteLottie : SetGoalSideEffect()
}
