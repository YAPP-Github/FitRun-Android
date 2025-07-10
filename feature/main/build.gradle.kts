@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

import com.yapp.fitrun.setNamespace

plugins {
    alias(libs.plugins.fitrun.android.feature)
    alias(libs.plugins.fitrun.android.compose)
}

android {
    setNamespace("feature.main")
}

// Add library
dependencies {
    implementations(
        libs.androidx.core.ktx,

        projects.feature.navigator,
        projects.feature.home,
        projects.feature.record,
        projects.feature.crew,
        projects.feature.mypage,
        projects.feature.onboarding,
    )
}