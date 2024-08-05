package com.chill.mallang.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.ui.theme.Gray4
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography

@Composable
fun LongBlackButtonWithTimer(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    remainingTime: Int = 0,
    timeLimit: Int = 1,
    textSize: TextUnit = 13.sp,
    buttonText: String = ""
) {
    val progress = remember { mutableStateOf(1f) }

    LaunchedEffect(remainingTime) {
        if (remainingTime > 0) {
            progress.value = remainingTime.toFloat() / timeLimit
        }
    }

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = White,
            containerColor = Gray6
        ),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Gray4),
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = buttonText,
                style = Typography.displayLarge,
                fontSize = textSize,
                modifier = modifier.align(Alignment.Center)
            )
            Box(
                modifier = modifier
                    .background(Color.Transparent)
                    .align(Alignment.CenterEnd),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = progress.value,
                    modifier = Modifier.size(30.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )

                Text(
                    text = "$remainingTime",
                    fontSize = textSize,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center),
                    style = Typography.titleSmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LongBlackButtonWithTimerPreview() {
    MallangTheme {
        LongBlackButtonWithTimer(buttonText = "테스트")
    }
}
