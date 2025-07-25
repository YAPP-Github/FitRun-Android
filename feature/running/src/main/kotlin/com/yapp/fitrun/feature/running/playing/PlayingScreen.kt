package com.yapp.fitrun.feature.running.playing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yapp.fitrun.core.designsystem.Number_number1_bold
import com.yapp.fitrun.core.designsystem.Number_number2_semiBold
import com.yapp.fitrun.core.designsystem.R

@Composable
internal fun PlayingRoute() {
    PlayingScreen()
}

@Composable
internal fun PlayingScreen(
    onPauseClicked: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.fg_nuetral_gray1000)),
    ) {
        // 상단
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.TopEnd,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_volume_on), // pause 아이콘
                contentDescription = "On Volumn",
                modifier = Modifier
                    .padding(end = 20.dp, top = 20.dp)
                    .size(32.dp),
                tint = Color.White,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 142.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "1.87",
                    style = Number_number1_bold,
                    color = colorResource(R.color.fg_text_interactive_inverse),
                )
                Text(
                    text = "km",
                    fontSize = 34.sp,
                    color = colorResource(R.color.fg_text_interactive_inverse),
                )
                Spacer(modifier = Modifier.height(48.dp))
                Text(
                    text = "5'32\"",
                    style = Number_number2_semiBold,
                    color = colorResource(R.color.fg_text_interactive_inverse),
                )
                Text(
                    text = "평균 페이스",
                    fontSize = 18.sp,
                    color = colorResource(R.color.fg_text_interactive_inverse),
                )
            }
        }

        // 하단
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "20:25",
                    style = Number_number1_bold,
                    color = colorResource(R.color.fg_text_interactive_inverse),
                )
                Text(
                    text = "러닝 시간",
                    fontSize = 18.sp,
                    color = colorResource(R.color.fg_text_interactive_inverse),
                )

                Spacer(modifier = Modifier.height(76.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Button(
                        onClick = onPauseClicked,
                        modifier = Modifier.size(72.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.fg_text_interactive_inverse),
                        ),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_pause), // pause 아이콘
                            contentDescription = "Pause",
                            tint = Color.Black,
                        )
                    }

                    Button(
                        onClick = { },
                        modifier = Modifier.size(72.dp),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.fg_text_interactive_inverse),
                        ),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_stop), // stop 아이콘
                            contentDescription = "Stop",
                            tint = Color.Black,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PlayingScreenPreview() {
    PlayingScreen(
        onPauseClicked = {},
    )
}
