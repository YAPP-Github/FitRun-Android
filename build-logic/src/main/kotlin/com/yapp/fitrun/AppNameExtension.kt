package com.yapp.fitrun

import org.gradle.api.Project

fun Project.setNamespace(name: String) {
    androidExtension.apply {
        namespace = "com.yapp.fitrun.$name"
    }
}