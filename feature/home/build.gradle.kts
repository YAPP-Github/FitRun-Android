@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

import com.yapp.fitrun.setNamespace

plugins {
    alias(libs.plugins.fitrun.android.feature)
    alias(libs.plugins.fitrun.android.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    setNamespace("feature.home")
}

// Add library
dependencies {
    implementations(
        libs.androidx.core.ktx,
        libs.kotlinx.serialization.json,
        libs.naver.map,
        libs.naver.map.location,
        libs.naver.map.compose,
        libs.accompanist.permissions,
        libs.accompanist.systemuicontroller,
        libs.play.services.location,
        projects.core.domain,
    )
}
