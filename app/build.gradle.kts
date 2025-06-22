@file:Suppress("INLINE_FROM_HIGHER_PLATFORM")

import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.fitrun.android.application)
    alias(libs.plugins.fitrun.android.compose)
}

android {
    namespace = "com.yapp.fitrun"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.yapp.fitrun"
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", gradleLocalProperties(rootDir, providers).getProperty("KAKAO_NATIVE_APP_KEY"))
        addManifestPlaceholders(mapOf("KAKAO_REDIRECT_URI" to gradleLocalProperties(rootDir, providers).getProperty("KAKAO_REDIRECT_URI")))
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementations(
        // core
        project(path = ":core:common"),
        project(path = ":core:data"),
        project(path = ":core:datastore"),
        project(path = ":core:design-system"),
        project(path = ":core:domain"),
        project(path = ":core:network"),
        project(path = ":core:ui"),

        // feature
        project(path = ":feature:home"),
        project(path = ":feature:login"),
        project(path = ":feature:splash"),

        // library
        libs.androidx.core.ktx,
        libs.androidx.activity.compose,
        libs.kakao.auth
    )
}