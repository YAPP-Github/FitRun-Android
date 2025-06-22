@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
    alias(libs.plugins.gradle.dependency.handler.extensions)
}

group = "com.yapp.fitrun.buildlogic"

dependencies {
    implementations(
        libs.android.gradlePlugin,
        libs.kotlin.gradlePlugin,
        libs.verify.detektPlugin,
        libs.plugin.kotlin.serializationPlugin,
        libs.compose.compiler.gradle.plugin
    )
}

gradlePlugin {
    plugins {
        register("androidHilt") {
            id = "fitrun.android.hilt"
            implementationClass = "com.yapp.fitrun.HiltAndroidPlugin"
        }
        register("kotlinHilt") {
            id = "fitrun.kotlin.hilt"
            implementationClass = "com.yapp.fitrun.HiltKotlinPlugin"
        }
    }
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}