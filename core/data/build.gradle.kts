@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

import com.yapp.fitrun.setNamespace

plugins {
    alias(libs.plugins.fitrun.android.library)
    alias(libs.plugins.fitrun.android.hilt)
}

android {
    setNamespace("core.data")
}

// Add library
dependencies {
    implementations(
        projects.core.common,
        projects.core.network,
        projects.core.datastore,
        projects.core.domain,

        libs.retrofit.core,
        libs.retrofit.kotlin.serialization,
        libs.kotlinx.serialization.json,
    )
}