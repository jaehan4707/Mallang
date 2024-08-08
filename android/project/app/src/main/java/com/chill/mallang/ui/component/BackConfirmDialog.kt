package com.chill.mallang.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.chill.mallang.R
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography

@Composable
fun BackConfirmDialog(
    content: String = "",
    title: String = "",
    onConfirmMessage: String = "",
    onDismissMessage: String = "",
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
        content = {
            Surface(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(10.dp),
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(width = 2.dp, color = Gray6),
                color = Color.White,
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        modifier = Modifier.size(150.dp, 150.dp),
                        painter = painterResource(id = R.mipmap.sad_mallang),
                        contentDescription = "",
                    )
                    if (title.isNotBlank()) {
                        Text(
                            text = title,
                            style = Typography.headlineLarge,
                            textAlign = TextAlign.Center,
                        )
                    }
                    if (content.isNotBlank()) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = content,
                            style = Typography.headlineMedium,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Row {
                        Spacer(modifier = Modifier.weight(0.3f))
                        Button(
                            onClick = onConfirm,
                            shape = RoundedCornerShape(10.dp),
                            colors =
                                ButtonDefaults.buttonColors(
                                    contentColor = White,
                                    containerColor = Color.Black,
                                ),
                        ) {
                            Text(
                                text = onConfirmMessage,
                                style = Typography.displayMedium,
                            )
                        }
                        Spacer(modifier = Modifier.weight(0.1f))
                        Button(
                            shape = RoundedCornerShape(10.dp),
                            onClick = onDismiss,
                            colors =
                                ButtonDefaults.buttonColors(
                                    contentColor = White,
                                    containerColor = Color.Black,
                                ),
                        ) {
                            Text(
                                text = onDismissMessage,
                                style = Typography.displayMedium,
                            )
                        }
                        Spacer(modifier = Modifier.weight(0.3f))
                    }
                }
            }
        },
    )
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun ConfirmDialogPreview() {
    MallangTheme {
        BackConfirmDialog(title = "다이얼로그", content = "컨텐츠")
    }
}
