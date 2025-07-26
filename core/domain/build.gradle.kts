@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    alias(libs.plugins.fitrun.kotlin.library)
}

// Add library
dependencies {
    implementation(libs.coroutines.core)
}
