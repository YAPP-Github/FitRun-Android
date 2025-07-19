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
        addManifestPlaceholders(mapOf("NAVER_MAP_CLIENT_KEY" to gradleLocalProperties(rootDir, providers).getProperty("NAVER_MAP_CLIENT_KEY")))
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
        projects.core.common,
        projects.core.data,
        projects.core.datastore,
        projects.core.designsystem,
        projects.core.domain,
        projects.core.network,
        projects.core.ui,

        // feature
        projects.feature.home,
        projects.feature.login,
        projects.feature.splash,
        projects.feature.main,
        projects.feature.navigator,
        projects.feature.onboarding,
        projects.feature.record,
        projects.feature.crew,
        projects.feature.mypage,

        // library
        libs.androidx.core.ktx,
        libs.androidx.activity.compose,
        libs.kakao.auth
    )
}