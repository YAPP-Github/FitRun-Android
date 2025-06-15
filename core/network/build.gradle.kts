@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

import com.yapp.fitrun.setNamespace

plugins {
    alias(libs.plugins.fitrun.android.library)
    alias(libs.plugins.fitrun.android.hilt)
}

android {
    setNamespace("core.network")

    buildFeatures {
        buildConfig = true
    }
}

// Add library
dependencies {
    implementations(
        project(path = ":core:datastore"),

        libs.retrofit.core,
        libs.retrofit.kotlin.serialization
    )
}