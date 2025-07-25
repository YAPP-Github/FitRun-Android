
plugins {
    alias(libs.plugins.gradle.dependency.handler.extensions)
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.verify.detekt) apply false
    alias(libs.plugins.kotlin.ktlint) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.android.library) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

allprojects {
    apply {
        plugin(rootProject.libs.plugins.gradle.dependency.handler.extensions.get().pluginId)
    }
    plugins.withType<org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper> {
        apply(plugin = rootProject.libs.plugins.kotlin.ktlint.get().pluginId)
    }
}

// ktlint 설정
subprojects {
    apply(plugin = rootProject.libs.plugins.kotlin.ktlint.get().pluginId)
    
    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        version.set("0.50.0")
        android.set(true)
        ignoreFailures.set(false)
        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
        }
        filter {
            exclude("**/generated/**")
            exclude("**/build/**")
        }
    }
}
