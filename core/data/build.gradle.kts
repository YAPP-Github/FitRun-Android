@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

import com.yapp.fitrun.setNamespace

plugins {
    alias(libs.plugins.fitrun.android.library)
}

android {
    setNamespace("core.data")
}

// Add library
dependencies {
    implementations(
        project(path = ":core:network"),
        project(path = ":core:datastore"),
        project(path = ":core:domain"),

        libs.retrofit.core,
        libs.retrofit.kotlin.serialization
    )
}