package com.yapp.fitrun.feature.setgoal.component

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yapp.fitrun.core.designsystem.Body_body3_semiBold
import com.yapp.fitrun.core.designsystem.Body_body4_regular
import com.yapp.fitrun.core.designsystem.Head_h1_bold
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.core.designsystem.pretendardFamily
import com.yapp.fitrun.core.ui.ToggleSwitch

@Composable
fun SetRunningCountSection(
    modifier: Modifier = Modifier,
    initialCount: Int = 3,
    isRemindEnabled: Boolean = false,
    onRunningCountChange: (Int) -> Unit = {},
    onRemindEnabledChange: (Boolean) -> Unit = {},
) {
    RunningCountInput(
        modifier = modifier,
        initialCount = initialCount,
        isRemindEnabled = isRemindEnabled,
        onRunningCountChange = onRunningCountChange,
        onRemindEnabledChange = onRemindEnabledChange,
    )
}

@Composable
fun RunningCountInput(
    modifier: Modifier = Modifier,
    initialCount: Int = 3,
    isRemindEnabled: Boolean = false,
    onRunningCountChange: (Int) -> Unit = {},
    onRemindEnabledChange: (Boolean) -> Unit = {},
) {
    val scrollState = rememberScrollState()
    var runningCountText by remember { mutableStateOf(initialCount.toString()) }
    var isAlarm by remember { mutableStateOf(isRemindEnabled) }

    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Text(
            text = "일주일에",
            style = Body_body3_semiBold,
            color = colorResource(R.color.fg_text_secondary),
        )
        RunningCountTextField(
            value = runningCountText,
            onValueChange = { newValue ->
                // 숫자만 허용
                val filteredValue = newValue.filter { it.isDigit() }
                // 최대 2자리까지만 허용
                val limitedValue = filteredValue.take(2)
                runningCountText = limitedValue

                // 유효한 숫자인 경우에만 콜백 호출
                limitedValue.toIntOrNull()?.let { count ->
                    if (count in 1..14) { // 1~14회까지만 허용
                        onRunningCountChange(count)
                    }
                }
            },
        )
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = colorResource(R.color.bg_secondary),
                    shape = RoundedCornerShape(16.dp),
                )
                .padding(vertical = 20.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = "리마인드 알림",
                    style = Body_body3_semiBold,
                    color = colorResource(R.color.fg_text_primary),
                )
                Text(
                    text = "오전 10시에 리마인드 알림을 보내드려요",
                    style = Body_body4_regular,
                    color = colorResource(R.color.fg_text_secondary),
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            ToggleSwitch(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .clickable {
                        isAlarm = !isAlarm
                        onRemindEnabledChange(isAlarm)
                    },
                checked = isAlarm,
            )
        }
    }
}

@Composable
private fun RunningCountTextField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .wrapContentSize(),
    ) {
        BasicTextField(
            modifier = Modifier.width(IntrinsicSize.Min),
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            textStyle = TextStyle(
                textAlign = TextAlign.End,
                fontFamily = pretendardFamily,
                fontWeight = FontWeight(LocalContext.current.resources.getInteger(R.integer.font_weight_normal_bold)),
                fontSize = 52.sp,
                color = colorResource(R.color.fg_text_primary),
                lineHeight = dimensionResource(id = R.dimen.line_height_1700).value.sp,
                letterSpacing = dimensionResource(id = R.dimen.number_letter_spacing).value.sp,
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.None,
                ),
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            decorationBox = { innerTextField ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    innerTextField()
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(colorResource(R.color.bg_interactive_primary)),
                    )
                }
            },
        )

        Text(
            text = "회",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(top = 10.dp),
            textAlign = TextAlign.Center,
            color = colorResource(R.color.fg_text_disabled),
            style = Head_h1_bold,
        )
    }
}
