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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chill.mallang.R
import com.chill.mallang.ui.component.BackConfirmHandler
import com.chill.mallang.ui.theme.Gray3
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography

@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    popUpBackStack: () -> Unit = {},
    navigateToQuizResult: (Int) -> Unit = {},
) {

    val quizViewModel: QuizViewModel = hiltViewModel()
    val quizState = quizViewModel.state

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
                quizTitle = quizState.quizTitle,
                quizScript = quizState.quizScript
            )
            Spacer(modifier = Modifier.weight(1f))
            AnswerList(
                viewModel = quizViewModel,
                state = quizState,
                fraction = 0.13f,
                onAnswerSelected = { selectedIndex ->
                    quizViewModel.selectAnswer(selectedIndex)
                }
            )
        }
        Button(
            onClick = {
                quizViewModel.submitQuiz() // 퀴즈 제출 및 채점
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
    quizTitle: String,
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
                    text = quizTitle,
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
    state: Any,
    viewModel: QuizViewModel,
    fraction: Float,
    onAnswerSelected: (Int) -> Unit = { }
) {

    var size: Int = -1

    if (state is QuizState) {
        size = state.wordList.size
    } else if (state is QuizResultState) {
        size = state.wordList.size
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize()
    ) {
        items(size) { index ->
            if (state is QuizState) {
                AnswerListItem(
                    modifier = Modifier.fillParentMaxHeight(fraction),
                    index = index,
                    viewModel = viewModel,
                    state = state,
                    onItemClick = { selectedIndex ->
                        onAnswerSelected(selectedIndex)
                    }
                )
            } else if (state is QuizResultState) {
                AnswerResultListItem(
                    modifier = Modifier.fillParentMaxHeight(fraction),
                    index = index,
                    state = state,
                    onItemClick = { selectedIndex ->
                        onAnswerSelected(selectedIndex)
                    }
                )
            }
        }
    }
}

@Composable
fun AnswerListItem(
    modifier: Modifier = Modifier,
    index: Int,
    viewModel: QuizViewModel,
    state: QuizState,
    onItemClick: (Int) -> Unit
) {
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
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                    .border(2.dp, color = Gray6, shape = RoundedCornerShape(8.dp))
                    .clickable { onItemClick(index) }
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = Gray6,
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
                    text = state.wordList[index],
                    modifier = Modifier.weight(1f),
                    style = Typography.headlineLarge
                )
                if (viewModel.selectedAnswer == index + 1) {
                    Icon(
                        modifier = Modifier.padding(10.dp),
                        painter = painterResource(id = R.drawable.ic_check),
                        contentDescription = null
                    )
                }

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
