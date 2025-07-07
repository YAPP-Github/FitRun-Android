package com.yapp.fitrun.feature.splash.viewmodel

data class FitRunSplashState (
    val showSplash: Boolean = true,
    val showWorkThrough: Boolean = false,

    // Work Through
    val titleTextList: List<Int> = emptyList(),
    val descriptionTextList: List<Int> = emptyList(),
)