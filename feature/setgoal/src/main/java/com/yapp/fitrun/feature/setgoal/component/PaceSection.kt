package com.yapp.fitrun.feature.setgoal.component

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yapp.fitrun.core.designsystem.Body_body3_semiBold
import com.yapp.fitrun.core.designsystem.Caption_caption2_semiBold
import com.yapp.fitrun.core.designsystem.R
import com.yapp.fitrun.core.designsystem.pretendardFamily
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun SetPaceSection(
    modifier: Modifier = Modifier,
    initialPace: Int = 420, // 초 단위, 기본값 7'00" = 420초
    onPaceChange: (Int) -> Unit = {},
) {
    PaceInputWithProgress(
        modifier = modifier,
        initialPace = initialPace,
        onPaceChange = { paceSeconds ->
            onPaceChange(paceSeconds)
        },
    )
}

// PaceInputWithProgress 컴포저블도 수정
@Composable
fun PaceInputWithProgress(
    modifier: Modifier = Modifier,
    initialPace: Int = 420,
    onPaceChange: (Int) -> Unit = {},
) {
    // 초기 페이스 텍스트 계산
    val initialMinutes = initialPace / 60
    val initialSeconds = initialPace % 60
    val initialPaceText = String.format(Locale.getDefault(), "%d'%02d\"", initialMinutes, initialSeconds)
    var paceText by remember { mutableStateOf(initialPaceText) }
    var isFocused by remember { mutableStateOf(false) }

    // 초기 슬라이더 값 계산
    val initialSliderValue = when (initialPace) {
        360 -> 2f // 8'00"
        420 -> 1f // 7'00"
        480 -> 0f // 6'00"
        else -> when {
            initialPace <= 390 -> 0f
            initialPace <= 450 -> 1f
            else -> 2f
        }
    }

    var sliderValue by remember { mutableFloatStateOf(initialSliderValue) }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Text(
            text = "일주일에",
            style = Body_body3_semiBold,
            color = colorResource(R.color.fg_text_secondary),
        )

        // Pace Input TextField
        PaceTextField(
            value = paceText,
            onValueChange = { newValue ->
                paceText = newValue
                val seconds = parsePaceToSeconds(newValue)
                if (seconds != null) {
                    onPaceChange(seconds)
                    // 텍스트 입력에 따라 슬라이더 값도 업데이트
                    sliderValue = when {
                        seconds <= 390 -> 2f // 8'00"
                        seconds <= 450 -> 1f // 7'30" 이하는 7'00"
                        else -> 0f // 6'00"
                    }
                }
            },
            isFocused = isFocused,
            onFocusChanged = { focused ->
                isFocused = focused
            },
        )
        Spacer(modifier = Modifier.height(40.dp))
        // Custom Styled Slider
        CustomPaceSlider(
            value = sliderValue,
            onValueChange = { newValue ->
                sliderValue = newValue
                // 슬라이더 값에 따라 페이스 텍스트 업데이트
                val roundedValue = newValue.roundToInt()
                val paceSeconds = when (roundedValue) {
                    0 -> 480 // 8'00"
                    1 -> 420 // 7'00"
                    2 -> 360 // 6'00"
                    else -> 420
                }
                val minutes = paceSeconds / 60
                val seconds = paceSeconds % 60
                paceText = String.format(Locale.getDefault(), "%d'%02d\"", minutes, seconds)
                onPaceChange(paceSeconds)
            },
        )
    }
}

@Composable
fun SetPaceOnBoardingSection(
    modifier: Modifier = Modifier,
    initialPace: Int = 420, // 초 단위, 기본값 7'00" = 420초
    onPaceChange: (Int) -> Unit = {},
) {
    // 초기 페이스 텍스트 계산
    val initialMinutes = initialPace / 60
    val initialSeconds = initialPace % 60
    val initialPaceText = String.format(Locale.getDefault(), "%d'%02d\"", initialMinutes, initialSeconds)

    var paceText by remember { mutableStateOf(initialPaceText) }
    var isFocused by remember { mutableStateOf(false) }

    // 초기 슬라이더 값 계산
    val initialSliderValue = when (initialPace) {
        360 -> 2f // 8'00"
        420 -> 1f // 7'00"
        480 -> 0f // 6'00"
        else -> when {
            initialPace <= 390 -> 0f
            initialPace <= 450 -> 1f
            else -> 2f
        }
    }

    var sliderValue by remember { mutableFloatStateOf(initialSliderValue) }

    Column(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "나의 페이스는",
            style = Body_body3_semiBold,
            color = colorResource(R.color.fg_text_secondary),
        )

        // Pace Input TextField
        PaceTextField(
            value = paceText,
            onValueChange = { newValue ->
                paceText = newValue
                val seconds = parsePaceToSeconds(newValue)
                if (seconds != null) {
                    onPaceChange(seconds)
                    // 텍스트 입력에 따라 슬라이더 값도 업데이트
                    sliderValue = when {
                        seconds <= 390 -> 2f // 8'00"
                        seconds <= 450 -> 1f // 7'30" 이하는 7'00"
                        else -> 0f // 6'00"
                    }
                }
            },
            isFocused = isFocused,
            onFocusChanged = { focused ->
                isFocused = focused
            },
        )
        Spacer(modifier = Modifier.height(40.dp))
        // Custom Styled Slider
        CustomPaceSlider(
            value = sliderValue,
            onValueChange = { newValue ->
                sliderValue = newValue
                // 슬라이더 값에 따라 페이스 텍스트 업데이트
                val roundedValue = newValue.roundToInt()
                val paceSeconds = when (roundedValue) {
                    0 -> 480 // 8'00"
                    1 -> 420 // 7'00"
                    2 -> 360 // 6'00"
                    else -> 420
                }
                val minutes = paceSeconds / 60
                val seconds = paceSeconds % 60
                paceText = String.format(Locale.getDefault(), "%d'%02d\"", minutes, seconds)
                onPaceChange(paceSeconds)
            },
        )
    }
}

@Composable
private fun PaceTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isFocused: Boolean,
    onFocusChanged: (Boolean) -> Unit,
) {
    val underlineColor by animateColorAsState(
        targetValue = if (isFocused) Color(0xFFFF6B35) else Color.Transparent,
        label = "underlineAnimation",
    )

    val underlineHeight by animateDpAsState(
        targetValue = if (isFocused) 2.dp else 0.dp,
        label = "underlineHeightAnimation",
    )

    // TextFieldValue를 사용하여 커서 위치 제어
    var textFieldValue by remember(value) {
        mutableStateOf(TextFieldValue(text = value, selection = TextRange(value.length)))
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            BasicTextField(
                value = textFieldValue,
                onValueChange = { newValue ->
                    val oldText = textFieldValue.text
                    val newText = newValue.text

                    // 숫자만 추출
                    val digitsOnly = newText.filter { it.isDigit() }

                    // 최대 3자리까지만 허용 (9'59" 까지 가능)
                    val limitedDigits = digitsOnly.take(3)

                    // 포맷팅
                    val formattedValue = when (limitedDigits.length) {
                        0 -> ""
                        1 -> "${limitedDigits[0]}'"
                        2 -> "${limitedDigits[0]}'${limitedDigits[1]}"
                        3 -> "${limitedDigits[0]}'${limitedDigits[1]}${limitedDigits[2]}\""
                        else -> value // 이 경우는 발생하지 않음
                    }

                    // 커서 위치 계산
                    val cursorPosition = when {
                        // 새로운 문자가 추가되었을 때
                        formattedValue.length > oldText.length -> {
                            when (limitedDigits.length) {
                                1 -> 2 // 1' 다음 위치
                                2 -> 3 // 1'2 다음 위치
                                3 -> 5 // 1'23" 다음 위치
                                else -> formattedValue.length
                            }
                        }
                        // 삭제되었을 때는 기본 동작 유지
                        else -> newValue.selection.start.coerceAtMost(formattedValue.length)
                    }

                    textFieldValue = TextFieldValue(
                        text = formattedValue,
                        selection = TextRange(cursorPosition),
                    )

                    onValueChange(formattedValue)
                },
                modifier = Modifier
                    .onFocusChanged { focusState ->
                        onFocusChanged(focusState.isFocused)
                    },
                textStyle = TextStyle(
                    textAlign = TextAlign.Center,
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
                singleLine = true,
            )
        }
        // Animated Underline
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(underlineHeight)
                .background(underlineColor),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomPaceSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
) {
    Column {
        // Custom Slider with 3 steps
        Slider(
            value = value,
            onValueChange = { newValue ->
                // 가장 가까운 단계로 스냅
                val snappedValue = when {
                    newValue < 0.5f -> 0f
                    newValue < 1.5f -> 1f
                    else -> 2f
                }
                onValueChange(snappedValue)
            },
            valueRange = 0f..2f,
            steps = 1, // 3개의 위치를 만들기 위해 1 step (0, 1, 2)
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            colors = SliderDefaults.colors(
                thumbColor = Color.Black,
                activeTrackColor = Color.Black,
                inactiveTrackColor = Color.LightGray,
                activeTickColor = Color.Transparent,
                inactiveTickColor = Color.Transparent,
            ),
            thumb = {
                // Custom Thumb
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(Color.Black),
                )
            },
        )
        // Labels
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = "워밍업",
                    style = Caption_caption2_semiBold,
                    color = colorResource(R.color.fg_text_tertiary),
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "루틴",
                    style = Caption_caption2_semiBold,
                    color = colorResource(R.color.fg_text_tertiary),
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "챌린지",
                    style = Caption_caption2_semiBold,
                    color = colorResource(R.color.fg_text_tertiary),
                )
            }
        }
    }
}

private fun parsePaceToSeconds(paceText: String): Int? {
    return try {
        // 숫자만 추출
        val digitsOnly = paceText.filter { it.isDigit() }

        when (digitsOnly.length) {
            0 -> null
            1 -> digitsOnly.toInt() * 60 // 분만 입력된 경우
            2 -> {
                val minutes = digitsOnly[0].toString().toInt()
                val tensOfSeconds = digitsOnly[1].toString().toInt()
                minutes * 60 + tensOfSeconds * 10 // 두 번째 숫자는 초의 십의 자리
            }
            3 -> {
                val minutes = digitsOnly[0].toString().toInt()
                val seconds = digitsOnly.substring(1).toInt()
                if (seconds > 59) null else minutes * 60 + seconds
            }
            else -> null
        }
    } catch (e: NumberFormatException) {
        Log.w("Parser", "Invalid pace format: $paceText", e)
        null
    }
}
