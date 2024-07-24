package com.chill.mallang.ui.feature.quiz

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chill.mallang.R
import com.chill.mallang.ui.component.BackConfirmHandler
import com.chill.mallang.ui.theme.Gray3
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.Green1
import com.chill.mallang.ui.theme.Green2
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Sub1
import com.chill.mallang.ui.theme.Sub2
import com.chill.mallang.ui.theme.Typography

@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    popUpBackStack: () -> Unit = {},
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

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.height(15.dp))
            QuizBox(
                systemMessage = "빈칸을 채워 주세요",
                quizScript = "우리나라의 경제는 그동안 세계에 유례가 없을 정도로 ___ 할만한 성장을 이루었다."
            )
            Spacer(modifier = Modifier.weight(1f))
            AnswerList(
                isResultScreen = false
            )
        }
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(y = (-30).dp) // 버튼을 20dp 위로 올
                .widthIn(min = 180.dp) // 버튼의 최소 너비
                .heightIn(min = 80.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Gray6
            ),
            shape = RoundedCornerShape(20.dp, 0.dp, 0.dp, 20.dp)
        ) {
            Text(
                text = "제출하기      >",
                style = Typography.headlineLarge
            )
        }
    }
}

@Composable
fun QuizBox(
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
                text = quizScript,
                style = Typography.headlineSmall
            )
        }
    }
}

@Composable
fun AnswerList(
    isResultScreen: Boolean,
    userAnswer: QuizItem? = null,
    systemAnswer: QuizItem? = null
) {
    // 더미 데이터
    val wordList = arrayListOf(
        QuizItem(1, "괄목"),
        QuizItem(2, "상대"),
        QuizItem(3, "과장"),
        QuizItem(4, "시기")
    )

    var selectedItem by remember { mutableStateOf<QuizItem?>(null) }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize()
    ) {
        items(wordList) { item ->
            val isAnswer = item.word == systemAnswer?.word

            AnswerListItem(
                modifier = Modifier.fillParentMaxHeight(0.13f),
                item = item,
                isSelected = item == selectedItem,
                isAnswer = isAnswer, // 정답 아이템
                isResultScreen = isResultScreen,
                userAnswer = userAnswer,
                onItemClick = { clickedItem ->
                    selectedItem = if (selectedItem == clickedItem) null else clickedItem
                }
            )
        }
    }
}

@Composable
fun AnswerListItem(
    modifier: Modifier = Modifier,
    item: QuizItem,
    isSelected: Boolean,
    isAnswer: Boolean,
    isResultScreen: Boolean,
    userAnswer: QuizItem? = null,
    onItemClick: (QuizItem) -> Unit = {}
) {
    // 배경색
    val backgroundColor = when {
        isResultScreen && userAnswer?.word == item.word && !isAnswer -> Sub2
        isResultScreen && isAnswer -> Green2
        else -> Color.White
    }

    // 번호, 테두리 색상
    val borderColor = when {
        isResultScreen && userAnswer?.word == item.word && !isAnswer -> Sub1
        isResultScreen && isAnswer -> Green1
        else -> Gray6
    }

    Box(
        modifier = modifier
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
                .border(2.dp, color = borderColor, shape = RoundedCornerShape(8.dp))
                .clickable { onItemClick(item) }
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = borderColor,
                        shape = RoundedCornerShape(8.dp, 0.dp, 0.dp, 8.dp)
                    )
                    .fillMaxHeight()
                    .width(50.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "${item.number}",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = Typography.headlineLarge
                )
            }
            Spacer(modifier = Modifier.width(30.dp))
            Text(
                text = item.word,
                modifier = Modifier.weight(1f),
                style = Typography.headlineLarge
            )
            if (!isResultScreen && isSelected) {
                Icon(
                    modifier = Modifier.padding(10.dp),
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = null
                )
            } else if (isResultScreen && userAnswer?.word == item.word) {
                Icon(
                    modifier = Modifier.padding(10.dp),
                    painter = painterResource(id = R.drawable.ic_check),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun SubmitButton(
    title: String
) {
    
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun QuizPreview() {
    MallangTheme {
        QuizScreen()
    }
}
