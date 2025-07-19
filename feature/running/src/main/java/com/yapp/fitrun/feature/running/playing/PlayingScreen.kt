package com.yapp.fitrun.feature.running.playing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yapp.fitrun.core.designsystem.Body_body4_regular
import com.yapp.fitrun.core.designsystem.InteractiveInverse
import com.yapp.fitrun.core.designsystem.InteractiveSecondaryPressed
import com.yapp.fitrun.core.designsystem.Number_number1_Android
import com.yapp.fitrun.core.designsystem.Number_number3_bold
import com.yapp.fitrun.core.designsystem.TextPrimary
import com.yapp.fitrun.core.designsystem.TextTertiary
import com.yapp.fitrun.core.designsystem.gray_700
import com.yapp.fitrun.feature.running.R

@Composable
internal fun PlayingRoute() {
    PlayingScreen()
}

@Composable
internal fun PlayingScreen(
    onPauseClicked: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(InteractiveSecondaryPressed)
    ) {
        // 상단
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.TopEnd
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_volume_on), // pause 아이콘
                contentDescription = "On Volumn",
                modifier = Modifier
                    .padding(end = 20.dp, top = 20.dp)
                    .size(32.dp),
                tint = Color.White
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 142.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "1.87",
                    style = Number_number1_Android,
                    color = InteractiveInverse
                )
                Text(
                    text = "km",
                    fontSize = 34.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.SemiBold,
                    color = gray_700
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp))
                .background(Color.White)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        Text(
                            text = "평균 페이스",
                            style = Body_body4_regular,
                            color = TextTertiary
                        )
                        Text(
                            text = "12'51\"",
                            style = Number_number3_bold,
                            color = TextPrimary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(60.dp)
                            .background(Color(0xFFD9D9D9))
                    )

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "속력",
                            style = Body_body4_regular,
                            color = TextTertiary
                        )
                        Text(
                            text = "127",
                            style = Number_number3_bold,
                            color = TextPrimary
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(16.dp),
                ) {
                    Text(
                        text = "시간",
                        style = Body_body4_regular,
                        color = TextTertiary
                    )
                    Text(
                        text = "15:00",
                        style = Number_number3_bold,
                        color = TextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(37.dp))

                Button(
                    onClick = onPauseClicked,
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5500)),
                    modifier = Modifier
                        .size(110.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_pause), // pause 아이콘
                        contentDescription = "Pause",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun abc() {
    PlayingScreen()
}
