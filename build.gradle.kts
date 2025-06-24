
plugins {
    alias(libs.plugins.gradle.dependency.handler.extensions)
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.verify.detekt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.android.library) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
//
//    dependencies {
//        classpath(libs.kotlin.gradlePlugin)
//    }
}

allprojects {
    apply {
        plugin(rootProject.libs.plugins.gradle.dependency.handler.extensions.get().pluginId)
    }
}
