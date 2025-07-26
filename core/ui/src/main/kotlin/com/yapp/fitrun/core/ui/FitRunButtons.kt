package com.yapp.fitrun.core.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.fitrun.core.designsystem.Body_body3_semiBold
import com.yapp.fitrun.core.designsystem.R

@Composable
fun FitRunTextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String,
    textColor: Color = colorResource(R.color.fg_text_interactive_inverse),
    textStyle: TextStyle = Body_body3_semiBold,
    buttonColor: Color = colorResource(R.color.bg_interactive_primary),
) {
    FitRunBasicButton(
        modifier = modifier,
        onClick = onClick,
        buttonColor = buttonColor,
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = textColor,
            style = textStyle,
        )
    }
}

@Composable
fun FitRunTextIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String,
    textColor: Color = colorResource(R.color.fg_text_interactive_inverse),
    textStyle: TextStyle = Body_body3_semiBold,
    buttonColor: Color = colorResource(R.color.bg_interactive_primary),
    imageResource: Painter,
    iconModifier: Modifier = Modifier,
) {
    FitRunBasicButton(
        modifier = modifier,
        onClick = onClick,
        buttonColor = buttonColor,
    ) {
        Icon(
            painter = imageResource,
            contentDescription = "description",
            modifier = iconModifier,
            tint = Color.Unspecified,
        )

        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 4.dp),
            color = textColor,
            style = textStyle,
        )
    }
}

@Composable
fun FitRunBasicButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    buttonColor: Color = colorResource(R.color.bg_interactive_primary),
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
        ),
        onClick = onClick,
        content = content,
    )
}

@Preview
@Composable
fun FitRunTextButtonPreview() {
    FitRunTextButton(
        modifier = Modifier,
        onClick = {},
        text = "text",
    )
}

@Preview
@Composable
fun FitRunTextIconButtonPreview() {
    FitRunTextIconButton(
        modifier = Modifier,
        onClick = {},
        text = "text",
        imageResource = painterResource(R.drawable.ic_questionmark),
        buttonColor = colorResource(R.color.fg_nuetral_gray200),
    )
}
