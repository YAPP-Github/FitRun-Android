import com.yapp.fitrun.configureHiltAndroid
import com.yapp.fitrun.findLibrary

plugins {
    id("fitrun.android.library")
    id("fitrun.android.compose")
}

android {
    packaging {
        resources {
            excludes.add("META-INF/**")
        }
    }
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

configureHiltAndroid()

dependencies {
    implementation(findLibrary("hilt.navigation.compose"))
    implementation(findLibrary("androidx.compose.navigation"))
    implementation(findLibrary("androidx.lifecycle.viewModelCompose"))
    implementation(findLibrary("androidx.lifecycle.runtimeCompose"))
    implementation(findLibrary("orbit.viewmodel"))
    implementation(findLibrary("orbit.compose"))

    implementation(project(path = ":core:designsystem"))
    implementation(project(path = ":core:common"))
}