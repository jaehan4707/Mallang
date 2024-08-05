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
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.chill.mallang.R
import com.chill.mallang.ui.component.BackConfirmHandler
import com.chill.mallang.ui.feature.topbar.TopbarHandler
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.Typography

@Composable
fun WordNoteScreen(
    modifier: Modifier = Modifier,
    navigateToQuiz: (Int) -> Unit = {},
) {
    val wordViewModel: WordNoteViewModel = hiltViewModel()
    val state = wordViewModel.state

    var isWordScreen by remember { mutableStateOf(true) }
    var selectedWordIndex by remember { mutableStateOf<Int?>(null) }

    // TopBar
    val (navController, setNavController) = remember { mutableStateOf<NavController?>(null) }
    val (isBackPressed, setBackPressed) = remember { mutableStateOf(false) }

    BackConfirmHandler(
        isBackPressed = isBackPressed,
        onConfirm = {
            setBackPressed(false)
            navController?.popBackStack()
        },
        onDismiss = {
            setBackPressed(false)
        },
    )
    BackHandler(onBack = { setBackPressed(true) })

    TopbarHandler(
        title = if (isWordScreen) "단어장" else "오답노트",
        onBack = { nav ->
            setBackPressed(true)
            setNavController(nav)
        },
    )

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .background(color = White),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Button(
                modifier = Modifier.padding(12.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        contentColor = White,
                        containerColor = Gray6,
                    ),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    if (isWordScreen) {
                        wordViewModel.loadIncorrectWords()
                        isWordScreen = false
                    } else {
                        wordViewModel.loadWords()
                        isWordScreen = true
                    }
                },
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_change),
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Text(
                        text = if (isWordScreen) "오답노트로 변경" else "단어장으로 변경",
                        style = Typography.displayMedium,
                    )
                }
            }

            WordList(
                wordList = state.wordList,
                onWordClick = { index ->
                    selectedWordIndex = index
                },
            )
        }
        Button(
            onClick = {
                if (isWordScreen) {
                    navigateToQuiz(-1) // 일반적인 문제 풀이
                }
            },
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
                text = if (isWordScreen) "퀴즈 풀기 >" else "비슷한 문제 풀기 >",
                style = Typography.headlineLarge,
            )
        }
    }

    if (isWordScreen) {
        selectedWordIndex?.let { index ->
            WordCardDialog(
                index = index,
                wordCards = state.wordList,
                onDismiss = { selectedWordIndex = null },
            )
        }
    } else {
        // 오답노트일 때는 그때 풀었던 거 보여줌.
        selectedWordIndex?.let { index ->
            val word = state.wordList[index]
            if (word is Word.IncorrectWord) {
                navigateToQuiz(word.studyId)
            }
        }
    }
}

@Composable
fun WordList(
    wordList: List<Word>,
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
        modifier = Modifier.padding(horizontal = 12.dp),
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
    wordList: List<Word>,
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
        Text(
            text = if (wordList.isNotEmpty()) wordList[number - 1].word else "",
            style = Typography.headlineMedium,
        )
    }
    Box(modifier = Modifier.height(7.dp))
    HorizontalDivider(thickness = 2.dp, color = Gray6)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WordNotePreview() {
    WordNoteScreen()
}
