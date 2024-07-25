package com.chill.mallang.ui.feature.quiz

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
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
import androidx.compose.ui.draw.alpha
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
    submitQuiz: (Int) -> Unit = {},
    navigateToQuizResult: (Int) -> Unit = {}
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
            onClick = {
                navigateToQuizResult(1)
            },
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
    userAnswer: Int? = null,
    systemAnswer: Int? = null
) {
    // 더미 데이터
    val wordList = arrayListOf(
        "괄목" to "눈을 비비고 볼 정도로 매우 놀라다.",
        "상대" to "서로 마주 대하다.",
        "과장" to "사실보다 지나치게 불려서 말하거나 표현하다.",
        "시기" to "때나 경우."
    )

    var selectedItem by remember { mutableStateOf<Int?>(null) }
    var isExpandedItem by remember { mutableStateOf<Int?>(null) }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize()
    ) {
        items(wordList.size) { index ->
            val isAnswer = index + 1 == systemAnswer
            val fraction = if (!isResultScreen) 0.13f else 0.15f

            AnswerListItem(
                modifier = Modifier.fillParentMaxHeight(fraction),
                index = index,
                word = wordList[index].first,
                meaning = wordList[index].second,
                selectIndex = index + 1,
                isSelected = selectedItem == index + 1,
                isAnswer = isAnswer, // 정답 아이템
                isExpanded = isExpandedItem == index + 1,
                isResultScreen = isResultScreen,
                userAnswer = userAnswer,
                onItemClick = { clickedItem ->
                    if (!isResultScreen) {
                        selectedItem = clickedItem
                    } else {
                        isExpandedItem = if (isExpandedItem == clickedItem) null else clickedItem
                    }
                }
            )
        }
    }
}

@Composable
fun AnswerListItem(
    modifier: Modifier = Modifier,
    index: Int,
    word: String,
    meaning: String,
    selectIndex: Int,
    isSelected: Boolean,
    isAnswer: Boolean,
    isExpanded: Boolean,
    isResultScreen: Boolean,
    userAnswer: Int? = null,
    onItemClick: (Int) -> Unit = {}
) {
    // 배경색
    val backgroundColor = when {
        isResultScreen && userAnswer == selectIndex && !isAnswer -> Sub2
        isResultScreen && isAnswer -> Green2
        else -> Color.White
    }

    // 번호, 테두리 색상
    val borderColor = when {
        isResultScreen && userAnswer == selectIndex && !isAnswer -> Sub1
        isResultScreen && isAnswer -> Green1
        else -> Gray6
    }

    // 뜻 확장 및 애니메이션 효과
    val expandTransition = updateTransition(targetState = isExpanded, label = "expandTransition")
    val expandedHeight by expandTransition.animateDp(
        label = "expandedHeight",
        transitionSpec = { tween(durationMillis = 200) }
    ) { expanded ->
        if (expanded) 40.dp else 0.dp
    }

    val alpha by expandTransition.animateFloat(
        label = "alpha",
        transitionSpec = { tween(durationMillis = 200) }
    ) { expanded ->
        if (expanded) 1f else 0f
    }

    Column {
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
                    .clickable { onItemClick(selectIndex) }
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
                        text = "${index + 1}",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        style = Typography.headlineLarge
                    )
                }
                Spacer(modifier = Modifier.width(30.dp))
                Text(
                    text = word,
                    modifier = Modifier.weight(1f),
                    style = Typography.headlineLarge
                )
                if (!isResultScreen && isSelected) {
                    Icon(
                        modifier = Modifier.padding(10.dp),
                        painter = painterResource(id = R.drawable.ic_check),
                        contentDescription = null
                    )
                } else if (isResultScreen && userAnswer == index + 1) {
                    Icon(
                        modifier = Modifier.padding(10.dp),
                        painter = painterResource(id = R.drawable.ic_check),
                        contentDescription = null
                    )
                }
            }
        }
        if (isExpanded) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(expandedHeight)
                    .background(color = Color.Unspecified)
            ) {
                Text(
                    text = meaning,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .alpha(alpha),
                    style = Typography.displayMedium,
                    textAlign = TextAlign.Left
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun QuizPreview() {
    MallangTheme {
        QuizScreen()
    }
}
