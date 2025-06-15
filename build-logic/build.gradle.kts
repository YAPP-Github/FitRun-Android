plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.verify.detektPlugin)
    compileOnly(libs.compose.compiler.gradle.plugin)
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

