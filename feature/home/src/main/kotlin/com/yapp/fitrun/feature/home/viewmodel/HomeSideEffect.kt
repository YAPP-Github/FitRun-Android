package com.yapp.fitrun.feature.home.viewmodel

// Side Effects
sealed class HomeSideEffect {
    data class ShowError(val message: String) : HomeSideEffect()
    data class ShowLocationError(val message: String) : HomeSideEffect()
    object NavigateToRunning : HomeSideEffect()
    object NavigateToGoalSetting : HomeSideEffect()
}
