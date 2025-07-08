@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

import com.yapp.fitrun.setNamespace

plugins {
    alias(libs.plugins.fitrun.android.feature)
    alias(libs.plugins.fitrun.android.compose)
}

android {
    setNamespace("feature.splash")
}

// Add library
dependencies {
    implementations(
        libs.androidx.core.ktx,
        libs.androidx.splash,
        libs.kakao.auth,

        projects.feature.navigator,
        projects.core.domain,
    )
}