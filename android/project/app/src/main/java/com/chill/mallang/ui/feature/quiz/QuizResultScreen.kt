package com.chill.mallang.ui.feature.quiz

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.ui.component.BackConfirmHandler
import com.chill.mallang.ui.theme.Gray3
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.Green1
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Sub1
import com.chill.mallang.ui.theme.Typography

@Composable
fun QuizResultScreen(
    modifier: Modifier = Modifier,
    popUpBackStack: () -> Unit = {}
) {
    val isBackPressed = remember { mutableStateOf(false) }
    BackConfirmHandler(
        isBackPressed = isBackPressed.value,
        onConfirm = {
            isBackPressed.value = false
            popUpBackStack()
        },
        onDismiss = {
            isBackPressed.value = false
        }
    )
    BackHandler(onBack = { isBackPressed.value = true })

    // 사용자가 고른 정답
    val userData = 2

    // 정답 데이터
    val systemData = 1

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val title = if (userData == systemData) "정답!" else "오답!"
            val titleColor = if (title == "정답!") Green1 else Sub1

            Text(
                modifier = Modifier.padding(vertical = 10.dp),
                text = title,
                style = Typography.headlineLarge,
                fontSize = 40.sp,
                color = titleColor
            )
            QuizBoxWithUnderline(
                systemMessage = "빈칸을 채워 주세요",
                quizScript = "우리나라의 경제는 그동안 세계에 유례가 없을 정도로 괄목할 만한 성장을 이루었다.",
                underline = "괄목"
            )
            AnswerList(
                isResultScreen = true,
                userAnswer = userData,
                systemAnswer = systemData
            )
        }
    }
}

@Composable
fun QuizBoxWithUnderline(
    systemMessage: String,
    quizScript: String,
    underline: String
) {
    Box(
        modifier = Modifier
            .padding(12.dp)
            .border(width = 2.dp, color = Gray6, shape = RoundedCornerShape(10.dp))
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 30.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Q.",
                    style = Typography.headlineLarge
                )
                Box(modifier = Modifier.width(10.dp))
                Text(
                    text = systemMessage,
                    style = Typography.headlineMedium
                )
            }
            Box(modifier = Modifier.height(15.dp))
            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth()
                    .background(Gray3)
            )
            Box(modifier = Modifier.height(15.dp))
            Text(
                text = buildAnnotatedString {
                    val startIndex = quizScript.indexOf(underline)
                    if (startIndex != -1) {
                        append(quizScript.substring(0, startIndex))
                        withStyle(
                            style = SpanStyle(
                                textDecoration = TextDecoration.Underline
                            )
                        ) {
                            append(underline)
                        }
                        append(quizScript.substring(startIndex + underline.length))
                    } else {
                        append(quizScript)
                    }
                },
                style = Typography.headlineSmall
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ResultPreview() {
    MallangTheme {
        QuizResultScreen()
    }
}