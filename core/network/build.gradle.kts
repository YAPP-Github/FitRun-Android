@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

import com.yapp.fitrun.setNamespace

plugins {
    alias(libs.plugins.fitrun.android.library)
    alias(libs.plugins.fitrun.android.hilt)
    alias(libs.plugins.kotlin.serialization)
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
        libs.retrofit.core,
        libs.retrofit.kotlin.serialization,
        libs.okhttp3.core,
        libs.okhttp3.logging.interceptor,
        libs.kotlinx.serialization.json,
        projects.core.datastore,
    )
}