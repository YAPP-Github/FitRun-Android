package com.yapp.fitrun.feature.workthrough.viewmodel

sealed interface WorkThroughSideEffect {
    data object OnClickStartButton : WorkThroughSideEffect
}