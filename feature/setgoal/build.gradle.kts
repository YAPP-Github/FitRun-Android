@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

import com.yapp.fitrun.setNamespace

plugins {
    alias(libs.plugins.fitrun.android.feature)
    alias(libs.plugins.fitrun.android.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    setNamespace("feature.setgoal")
}

// Add library
dependencies {
    implementations(
        libs.androidx.core.ktx,
        libs.kotlinx.serialization.json,
        libs.lottie.compose,
        projects.core.ui,
        projects.core.designsystem,
        projects.core.domain,
    )
}
