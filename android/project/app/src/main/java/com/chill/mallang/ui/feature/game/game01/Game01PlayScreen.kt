package com.chill.mallang.ui.feature.game.game01

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import com.chill.mallang.ui.theme.Gray3
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography

@Composable
fun Game01PlayScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    val view = LocalView.current
    ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
        insets
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        QuestionBox(
            systemMessage = "지문을 요약하세요.",
            quizScript = "현대 사회에서 기술 발전은 일상생활에 큰 영향을 미치고 있다. 스마트폰과 같은 휴대용 기기의 보급으로 언제 어디서나 정보에 접근할 수 있게 되었으며, 이는 지식 탐구와 업무 효율성에 기여했다. 그러나 기술의 확산은 사생활 침해, 정보의 비대칭성, 디지털 격차와 같은 부정적인 측면도 있다. 디지털 격차는 정보 접근에 있어서 계층 간 불평등을 야기하며 사회 통합을 저해할 수 있다. 따라서 기술 발전의 영향을 다각도로 분석하고, 부정적인 영향을 최소화하는 정책이 필요하다."
        )
        AnswerBox()
        ButtonBox()
    }
}

@Composable
fun QuestionBox(
    systemMessage: String,
    quizScript: String
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
                    style = Typography.titleSmall,
                    fontSize = 20.sp
                )
                Box(modifier = Modifier.width(10.dp))
                Text(
                    text = systemMessage,
                    style = Typography.titleSmall,
                    fontSize = 20.sp
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
                text = quizScript + quizScript,
                style = Typography.titleSmall,
                fontSize = 20.sp,
                lineHeight = 30.sp
            )
        }
    }
}

@Composable
fun AnswerBox() {
    var text by remember { mutableStateOf("") }

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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Answer",
                    style = Typography.titleSmall,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
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
            BasicTextField(
                value = text,
                onValueChange = { newText ->
                    text = newText
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White)
                    .padding(8.dp),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black
                )
            )
        }
    }
}

@Composable
fun ButtonBox(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 50.dp),
            onClick = {},
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                Color.Black,
                Color.White
            )
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    "제출하기",
                    style = Typography.titleSmall,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
                ProgressWithText(100, modifier = Modifier.align(Alignment.CenterEnd))
            }
        }
    }
}

@Composable
fun ProgressWithText(duration: Int, modifier: Modifier = Modifier) {
    val remainingTime = remember { mutableStateOf(duration) }
    val progress = remember { mutableStateOf(1f) }
    val textSize = 18.sp

    LaunchedEffect(Unit) {
        while (remainingTime.value > 0) {
            kotlinx.coroutines.delay(1000)
            remainingTime.value -= 1
            progress.value = remainingTime.value.toFloat() / duration.toFloat()
        }
    }

    Box(
        modifier = modifier
            .size(100.dp)
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = progress.value,
            modifier = Modifier.size(30.dp),
            strokeWidth = 2.dp,
            color = Color.White
        )

        Text(
            text = "${remainingTime.value}",
            fontSize = textSize,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center),
            style = Typography.titleSmall
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Game01PlayScreenPreview(){
    MallangTheme {
        Game01PlayScreen()
    }
}
