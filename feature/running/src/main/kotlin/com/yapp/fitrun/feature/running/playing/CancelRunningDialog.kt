package com.yapp.fitrun.feature.running.playing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.yapp.fitrun.core.designsystem.Body_body3_medium
import com.yapp.fitrun.core.designsystem.Head_h5_bold
import com.yapp.fitrun.core.designsystem.R


@Composable
fun CancelRunningDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "러닝을 종료하시겠어요?",
                    color = colorResource(R.color.fg_text_primary),
                    style = Head_h5_bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "러닝을 종료하면 지금까지 달린 기록까지 저장돼요.",
                    color = colorResource(R.color.fg_text_primary),
                    style = Body_body3_medium
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = onConfirm,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.bg_interactive_selected)
                        )
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 6.dp),
                            text = "종료하기",
                            color = colorResource(R.color.fg_text_interactive_primary)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = onDismiss,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.fg_text_interactive_primary)
                        )
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 6.dp),
                            text = "달리기",
                            color = colorResource(R.color.fg_text_interactive_inverse)
                        )
                    }
                }
            }
        }
    }
}

// AlertDialog 사용 예제
@Preview
@Composable
fun AlertDialogExample() {

    CancelRunningDialog({}) { }


}