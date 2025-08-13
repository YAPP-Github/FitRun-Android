package com.yapp.fitrun.core.ui

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.yapp.fitrun.core.designsystem.R

@Composable
fun ToggleSwitch(
    checked: Boolean,
    modifier: Modifier = Modifier,
) {
    val switchWidth = 50.dp
    val switchHeight = 30.dp
    val padding = 2.dp
    val transition = updateTransition(checked, label = "switch_transition")

    val thumbSize by transition.animateDp(label = "thumb_size") { isChecked ->
        if (isChecked) 22.dp else 16.dp
    }

    val thumbOffsetX by transition.animateDp(label = "thumb_position") { isChecked ->
        if (isChecked) switchWidth - thumbSize - padding * 3 else padding
    }

    val backgroundColor by transition.animateColor(label = "bg_color") { isChecked ->
        if (isChecked) colorResource(R.color.bg_interactive_secondary_hoverd) else Color(0xFFD9D9D9)
    }

    Box(
        modifier = modifier
            .width(switchWidth)
            .height(switchHeight)
            .background(backgroundColor)
            .padding(padding),
        contentAlignment = Alignment.CenterStart,
    ) {
        Box(
            modifier = Modifier
                .size(thumbSize)
                .offset(x = thumbOffsetX)
                .background(color = colorResource(R.color.bg_primary), shape = CircleShape),
        )
    }
}
