@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

import com.yapp.fitrun.setNamespace

plugins {
    alias(libs.plugins.fitrun.android.library)
    alias(libs.plugins.fitrun.android.compose)
    alias(libs.plugins.fitrun.kotlin.hilt)
}

android {
    setNamespace("core.common")
}

// Add library
dependencies {
    implementations(
        libs.androidx.compose.navigation,
        libs.androidx.core.ktx,
        libs.androidx.appcompat,
        libs.hilt.navigation.compose,
    )
}
