package com.chill.mallang.ui.feature.word

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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
    val wordNoteState = wordViewModel.state

    var isWordScreen by remember { mutableStateOf(true) }
    var selectedWordIndex by remember { mutableStateOf<Int?>(null) }

    // TopBar
    val (navController, setNavController) = remember { mutableStateOf<NavController?>(null) }
    val (isBackPressed, setBackPressed) = remember { mutableStateOf(false) }

    // context
    val context = LocalContext.current

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
        isVisible = true,
        title = if (isWordScreen) context.getString(R.string.word_note) else context.getString(R.string.incorrect_word_note),
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
                        text =
                            if (isWordScreen) {
                                context.getString(R.string.change_to_incorrect_note)
                            } else {
                                context.getString(
                                    R.string.change_to_word_note,
                                )
                            },
                        style = Typography.displayMedium,
                    )
                }
            }

            AnimatedContent(
                targetState = isWordScreen,
                transitionSpec = {
                    pageFlipTransition(targetState)
                },
                modifier = Modifier.fillMaxSize(),
                label = "",
            ) { targetIsWordScreen ->
                WordList(
                    wordList = if (targetIsWordScreen) wordNoteState.wordList else wordNoteState.wordList,
                    onWordClick = { index ->
                        selectedWordIndex = index
                    },
                )
            }
        }
        if (isWordScreen) {
            Button(
                onClick = {
                    navigateToQuiz(-1) // 일반적인 문제 풀이
                },
                modifier =
                    Modifier
                        .align(Alignment.BottomEnd)
                        .offset(y = (-30).dp) // 버튼을 20dp 위로 올림
                        .width(180.dp)
                        .height(80.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = Gray6,
                    ),
                shape = RoundedCornerShape(20.dp, 0.dp, 0.dp, 20.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = context.getString(R.string.go_to_study_quiz),
                        style = Typography.headlineLarge,
                        textAlign = TextAlign.End,
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_next),
                        contentDescription = null,
                    )
                }
            }
        }
    }

    if (isWordScreen) {
        selectedWordIndex?.let { index ->
            WordCardDialog(
                index = index,
                wordCards = wordNoteState.wordList,
                onDismiss = { selectedWordIndex = null },
            )
        }
    } else {
        // 오답노트일 때는 그때 풀었던 거 보여줌.
        selectedWordIndex?.let { index ->
            val word = wordNoteState.wordList[index]
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
    val itemHeight = 33.dp // QuizListItem의 높이
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

// 페이지 넘기는 효과를 위한 함수
fun pageFlipTransition(targetState: Boolean): ContentTransform =
    (
        slideInHorizontally(
            initialOffsetX = { fullWidth -> if (targetState) -fullWidth else fullWidth },
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        ) +
            fadeIn(
                animationSpec = tween(durationMillis = 300, easing = LinearEasing),
            )
    ).togetherWith(
        slideOutHorizontally(
            targetOffsetX = { fullWidth -> if (targetState) fullWidth else -fullWidth },
            animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        ) +
            fadeOut(
                animationSpec = tween(durationMillis = 300, easing = LinearEasing),
            ),
    )

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WordNotePreview() {
    WordNoteScreen()
}
