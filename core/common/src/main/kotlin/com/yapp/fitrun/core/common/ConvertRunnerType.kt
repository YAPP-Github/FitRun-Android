package com.yapp.fitrun.core.common

fun convertRunnerType(type: String): String {
    return when (type) {
        "BEGINNER" -> "워밍업 러너"
        "INTERMEDIATE" -> "루틴 러너"
        "EXPERT" -> "챌린저 러너"
        else -> "워밍업 러너"
    }
}
