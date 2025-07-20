package com.yapp.fitrun.core.designsystem

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.platform.LocalContext

val pretendardFamily = FontFamily(
    Font(R.font.pretendard_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.pretendard_semi_bold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.pretendard_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.pretendard_regular, FontWeight.Normal, FontStyle.Normal),
)

// Headline Styles
val Head_h1_bold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_bold)),
        fontSize = dimensionResource(id = R.dimen.head_h1_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.head_h1_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.head_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Head_h1_semiBold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_semi_bold)),
        fontSize = dimensionResource(id = R.dimen.head_h1_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.head_h1_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.head_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Head_h2_bold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_bold)),
        fontSize = dimensionResource(id = R.dimen.head_h2_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.head_h2_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.head_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Head_h2_semiBold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_semi_bold)),
        fontSize = dimensionResource(id = R.dimen.head_h2_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.head_h2_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.head_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Head_h3_bold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_bold)),
        fontSize = dimensionResource(id = R.dimen.head_h3_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.head_h3_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.head_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Head_h3_semiBold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_semi_bold)),
        fontSize = dimensionResource(id = R.dimen.head_h3_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.head_h3_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.head_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Head_h4_bold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_bold)),
        fontSize = dimensionResource(id = R.dimen.head_h4_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.head_h4_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.head_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Head_h4_semiBold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_semi_bold)),
        fontSize = dimensionResource(id = R.dimen.head_h4_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.head_h4_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.head_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Head_h5_bold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_bold)),
        fontSize = dimensionResource(id = R.dimen.head_h5_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.head_h5_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.head_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Head_h5_semiBold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_semi_bold)),
        fontSize = dimensionResource(id = R.dimen.head_h5_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.head_h5_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.head_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Head_h6_bold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_bold)),
        fontSize = dimensionResource(id = R.dimen.head_h6_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.head_h6_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.head_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Head_h6_semiBold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_semi_bold)),
        fontSize = dimensionResource(id = R.dimen.head_h6_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.head_h6_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.head_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
// Body Styles
val Body_body1_bold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_bold)),
        fontSize = dimensionResource(id = R.dimen.body_body1_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.body_body1_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.body_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Body_body1_semiBold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_semi_bold)),
        fontSize = dimensionResource(id = R.dimen.body_body1_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.body_body1_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.body_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Body_body1_medium: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_medium)),
        fontSize = dimensionResource(id = R.dimen.body_body1_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.body_body1_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.body_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Body_body1_regular: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_regular)),
        fontSize = dimensionResource(id = R.dimen.body_body1_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.body_body1_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.body_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Body_body2_bold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_bold)),
        fontSize = dimensionResource(id = R.dimen.body_body2_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.body_body2_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.body_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Body_body2_semiBold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_semi_bold)),
        fontSize = dimensionResource(id = R.dimen.body_body2_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.body_body2_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.body_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Body_body2_medium: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_medium)),
        fontSize = dimensionResource(id = R.dimen.body_body2_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.body_body2_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.body_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Body_body2_regular: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_regular)),
        fontSize = dimensionResource(id = R.dimen.body_body2_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.body_body2_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.body_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Body_body3_bold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_bold)),
        fontSize = dimensionResource(id = R.dimen.body_body3_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.body_body3_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.body_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Body_body3_semiBold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_semi_bold)),
        fontSize = dimensionResource(id = R.dimen.body_body3_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.body_body3_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.body_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Body_body3_medium: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_medium)),
        fontSize = dimensionResource(id = R.dimen.body_body3_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.body_body3_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.body_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Body_body3_regular: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_regular)),
        fontSize = dimensionResource(id = R.dimen.body_body3_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.body_body3_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.body_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Body_body4_bold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_bold)),
        fontSize = dimensionResource(id = R.dimen.body_body4_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.body_body4_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.body_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Body_body4_semiBold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_semi_bold)),
        fontSize = dimensionResource(id = R.dimen.body_body4_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.body_body4_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.body_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Body_body4_medium: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_medium)),
        fontSize = dimensionResource(id = R.dimen.body_body4_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.body_body4_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.body_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Body_body4_regular: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_regular)),
        fontSize = dimensionResource(id = R.dimen.body_body4_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.body_body4_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.body_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
// Caption Styles
val Caption_caption1_bold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_bold)),
        fontSize = dimensionResource(id = R.dimen.caption_caption1_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.caption_caption1_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.caption_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Caption_caption1_semiBold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_semi_bold)),
        fontSize = dimensionResource(id = R.dimen.caption_caption1_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.caption_caption1_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.caption_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Caption_caption1_medium: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_medium)),
        fontSize = dimensionResource(id = R.dimen.caption_caption1_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.caption_caption1_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.caption_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Caption_caption2_bold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_bold)),
        fontSize = dimensionResource(id = R.dimen.caption_caption2_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.caption_caption2_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.caption_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Caption_caption2_semiBold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_semi_bold)),
        fontSize = dimensionResource(id = R.dimen.caption_caption2_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.caption_caption2_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.caption_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Caption_caption2_medium: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_medium)),
        fontSize = dimensionResource(id = R.dimen.caption_caption2_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.caption_caption2_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.caption_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Caption_caption3_bold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_bold)),
        fontSize = dimensionResource(id = R.dimen.caption_caption3_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.caption_caption3_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.caption_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Caption_caption3_semiBold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_semi_bold)),
        fontSize = dimensionResource(id = R.dimen.caption_caption3_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.caption_caption3_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.caption_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Caption_caption3_medium: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_medium)),
        fontSize = dimensionResource(id = R.dimen.caption_caption3_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.caption_caption3_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.caption_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Caption_caption4_bold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_bold)),
        fontSize = dimensionResource(id = R.dimen.caption_caption4_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.caption_caption4_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.caption_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Caption_caption4_semiBold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_semi_bold)),
        fontSize = dimensionResource(id = R.dimen.caption_caption4_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.caption_caption4_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.caption_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Caption_caption4_medium: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_medium)),
        fontSize = dimensionResource(id = R.dimen.caption_caption4_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.caption_caption4_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.caption_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
// Number Styles
val Number_number1_bold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_bold)),
        fontSize = dimensionResource(id = R.dimen.number_number1_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.number_number1_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.number_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Number_number1_extraBold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_extra_bold)),
        fontSize = dimensionResource(id = R.dimen.number_number1_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.number_number1_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.number_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Number_number1_semiBold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_semi_bold)),
        fontSize = dimensionResource(id = R.dimen.number_number1_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.number_number1_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.number_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Number_number2_bold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_bold)),
        fontSize = dimensionResource(id = R.dimen.number_number2_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.number_number2_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.number_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Number_number2_semiBold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_semi_bold)),
        fontSize = dimensionResource(id = R.dimen.number_number2_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.number_number2_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.number_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )
val Number_number3_bold: TextStyle
    @Composable get() = TextStyle(
        fontFamily = pretendardFamily,
        fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_bold)),
        fontSize = dimensionResource(id = R.dimen.number_number3_font_size).value.sp,
        lineHeight = dimensionResource(id = R.dimen.number_number3_line_height).value.sp,
        letterSpacing = dimensionResource(id = R.dimen.number_letter_spacing).value.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None
        )
    )



