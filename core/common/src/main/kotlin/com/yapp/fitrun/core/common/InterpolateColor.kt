package com.yapp.fitrun.core.common

import androidx.compose.ui.graphics.Color

fun interpolateColor(startColor: Color, endColor: Color, fraction: Float): Color {
    val startR = startColor.red
    val startG = startColor.green
    val startB = startColor.blue

    val endR = endColor.red
    val endG = endColor.green
    val endB = endColor.blue

    val r = lerp(startR, endR, fraction)
    val g = lerp(startG, endG, fraction)
    val b = lerp(startB, endB, fraction)

    return Color(r, g, b)
}

/**
 * 선형 보간 함수
 */
fun lerp(start: Float, end: Float, fraction: Float): Float {
    return start + (end - start) * fraction
}
