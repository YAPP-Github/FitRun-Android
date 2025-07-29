package com.yapp.fitrun.core.common

import java.util.Locale

fun formatMillisToString(milliseconds: Long): String {
    if (milliseconds <= 0) {
        return "--:--:--"
    }

    val totalSeconds = milliseconds / 1000
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
}
