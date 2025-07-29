package com.yapp.fitrun.core.common

import java.util.Locale

fun convertTimeToPace(milliseconds: Long): String {
    if (milliseconds <= 0) {
        return "-'-''"
    }

    val seconds = milliseconds / 1000
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60

    return String.format(Locale.getDefault(), "%d'%02d''", minutes, remainingSeconds)
}
