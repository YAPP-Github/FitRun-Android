@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

import com.yapp.fitrun.setNamespace
import com.yapp.fitrun.configureComposeFeature

plugins {
    alias(libs.plugins.fitrun.android.library)
}

configureComposeFeature()

android {
    setNamespace("core.common")
}

// Add library
dependencies {
    implementations(
        libs.androidx.core.ktx,
        libs.androidx.appcompat
    )
}