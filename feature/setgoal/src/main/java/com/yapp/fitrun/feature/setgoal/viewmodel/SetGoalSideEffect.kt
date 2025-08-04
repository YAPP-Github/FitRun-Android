package com.yapp.fitrun.feature.setgoal.viewmodel

sealed class SetGoalSideEffect {
    data class ShowSuccessToast(val message: String) : SetGoalSideEffect()
    data class ShowErrorToast(val message: String) : SetGoalSideEffect()
    data object NavigateToComplete : SetGoalSideEffect()
    data object ShowCompleteLottie : SetGoalSideEffect()
    data class NavigateToRecordDetail(val recordId: Int) : SetGoalSideEffect()
}
