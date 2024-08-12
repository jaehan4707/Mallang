package com.chill.mallang.ui.feature.game.game01.SubView.PlayScreen.Layout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.ui.theme.Gray3
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography

@Composable
fun AnswerBody(
    userAnswer: String,
    onUserAnswerChanged: (String) -> Unit,
    scrollToBottom: () -> Unit = {},
) {
    Box(
        modifier =
            Modifier
                .padding(12.dp)
                .border(width = 1.5.dp, color = Gray6, shape = RoundedCornerShape(10.dp))
                .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Answer",
                    style = Typography.titleSmall,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                )
            }
            Box(modifier = Modifier.height(15.dp))
            Spacer(
                modifier =
                    Modifier
                        .height(2.dp)
                        .fillMaxWidth()
                        .background(Gray3),
            )
            Box(modifier = Modifier.height(15.dp))
            BasicTextField(
                value = userAnswer,
                onValueChange = {
                    onUserAnswerChanged(it)
                    scrollToBottom()
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.White)
                        .padding(8.dp),
                textStyle =
                    TextStyle(
                        fontSize = 15.sp,
                        color = Color.Black,
                    ),
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AnswerBodyPreview() {
    MallangTheme {
        AnswerBody(
            userAnswer = "",
            onUserAnswerChanged = {},
        )
    }
}
