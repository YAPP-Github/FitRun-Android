@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

import com.yapp.fitrun.setNamespace

plugins {
    alias(libs.plugins.fitrun.android.library)
    alias(libs.plugins.fitrun.android.hilt)
}

android {
    setNamespace("core.datastore")
}

// Add library
dependencies {
    implementations(
        libs.androidx.core.ktx,
        libs.kotlinx.serialization.json,
        libs.datastore,
    )
}
