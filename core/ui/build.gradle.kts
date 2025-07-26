@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

import com.yapp.fitrun.setNamespace

plugins {
    alias(libs.plugins.fitrun.android.library)
    alias(libs.plugins.fitrun.android.compose)
}

android {
    setNamespace("core.ui")
}

// Add library
dependencies {
    implementations(
        projects.core.designsystem,

        libs.androidx.core.ktx,
        libs.androidx.appcompat,
    )
}
