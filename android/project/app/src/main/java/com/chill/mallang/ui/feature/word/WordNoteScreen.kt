package com.chill.mallang.ui.feature.word

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chill.mallang.ui.component.BackConfirmHandler
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.Typography

@Composable
fun WordNoteScreen(
    modifier: Modifier = Modifier,
    popUpBackStack: () -> Unit = {},
    navigateToQuiz: () -> Unit = {},
) {
    // 더미 데이터
    val wordList =
        arrayListOf(
            WordCard(
                word = "괄목",
                meaning = "눈을 비비고 볼 정도로 매우 놀라다.",
                example = "우리나라의 경제는 그동안 세계에 유례가 없을 정도로 괄목할 만한 성장을 이루었다.",
            ),
            WordCard(
                word = "상대",
                meaning = "서로 마주 대하다.",
                example = "우리나라의 경제는 그동안 세계에 유례가 없을 정도로 괄목할 만한 성장을 이루었다.",
            ),
            WordCard(
                word = "과장",
                meaning = "사실보다 지나치게 불려서 말하거나 행동하다.",
                example = "우리나라의 경제는 그동안 세계에 유례가 없을 정도로 괄목할 만한 성장을 이루었다.",
            ),
            WordCard(
                word = "시기",
                meaning = "때나 경우.",
                example = "우리나라의 경제는 그동안 세계에 유례가 없을 정도로 괄목할 만한 성장을 이루었다.",
            ),
        )

    var selectedWordIndex by remember { mutableStateOf<Int?>(null) }

    val isBackPressed = remember { mutableStateOf(false) }
    BackConfirmHandler(
        isBackPressed = isBackPressed.value,
        onConfirm = {
            isBackPressed.value = false
            popUpBackStack()
        },
        onDismiss = {
            isBackPressed.value = false
        },
    )
    BackHandler(onBack = { isBackPressed.value = true })

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .background(color = Color.White),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            WordList(wordList = wordList, onWordClick = { index ->
                selectedWordIndex = index
            })
        }
        Button(
            onClick = { navigateToQuiz() },
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .offset(y = (-30).dp) // 버튼을 20dp 위로 올
                    .widthIn(min = 180.dp) // 버튼의 최소 너비
                    .heightIn(min = 80.dp),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = Gray6,
                ),
            shape = RoundedCornerShape(20.dp, 0.dp, 0.dp, 20.dp),
        ) {
            Text(
                text = "퀴즈 풀기      >",
                style = Typography.headlineLarge,
            )
        }
    }

    selectedWordIndex?.let { index ->
        WordCardDialog(
            index = index,
            wordCards = wordList,
            onDismiss = { selectedWordIndex = null },
        )
    }
}

@Composable
fun WordList(
    wordList: List<WordCard>,
    onWordClick: (Int) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp // 휴대폰 스크린 높이
    val headerHeight = 55.dp // Header 높이
    val itemHeight = 30.dp // QuizListItem의 높이
    val itemSpacing = 10.dp // 아이템 사이 간격
    val padding = 15.dp // 패딩

    var totalItems =
        (((screenHeight - (padding * 2 + headerHeight)) / (itemHeight + itemSpacing)) - 1).toInt()

    // 만약 사용자 단어 개수가 저 개수를 넘으면 totalItems = wordList.size
    if (totalItems <= wordList.size) {
        totalItems = wordList.size
    }

    Box(
        modifier = Modifier.padding(12.dp),
    ) {
        LazyColumn(
            modifier =
                Modifier
                    .border(
                        border = BorderStroke(width = 2.dp, color = Gray6),
                        shape = RoundedCornerShape(2.dp),
                    ).padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(totalItems) { index ->
                if (index < wordList.size) {
                    QuizListItem(number = index + 1, wordList = wordList, onClick = {
                        onWordClick(index)
                    })
                } else {
                    QuizListItem(number = 0, wordList = emptyList(), onClick = {})
                }
            }
        }
    }
}

@Composable
fun QuizListItem(
    number: Int,
    wordList: List<WordCard>,
    onClick: () -> Unit,
) {
    Box(modifier = Modifier.height(7.dp))
    Row(
        modifier = Modifier.clickable(onClick = onClick),
    ) {
        Box(modifier = Modifier.width(4.dp))
        if (number != 0) {
            Text(
                text = "$number.",
                style = Typography.labelLarge, // 자간 조절 안 한 폰트
            )
        }
        Box(modifier = Modifier.width(4.dp))
        if (wordList.isNotEmpty()) {
            Text(
                text = wordList[number - 1].word,
                style = Typography.headlineMedium, // 자간 조절한 폰트
            )
        } else {
            Text(
                text = "",
                style = Typography.headlineMedium, // 자간 조절한 폰트
            )
        }
    }
    Box(modifier = Modifier.height(4.dp))
    HorizontalDivider(thickness = 2.dp, color = Gray6)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WordNotePreview() {
    WordNoteScreen()
}
