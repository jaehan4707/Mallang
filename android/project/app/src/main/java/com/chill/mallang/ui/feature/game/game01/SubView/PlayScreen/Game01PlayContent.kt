package com.chill.mallang.ui.feature.game.game01.SubView.PlayScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.R
import com.chill.mallang.data.model.entity.Game01QuizData
import com.chill.mallang.ui.feature.game.game01.Game01QUizUiState
import com.chill.mallang.ui.feature.game.game01.Game01ViewModel
import com.chill.mallang.ui.feature.game.game01.SubView.PlayScreen.Layout.AnswerBody
import com.chill.mallang.ui.feature.game.game01.SubView.PlayScreen.Layout.ButtonBody
import com.chill.mallang.ui.feature.game.game01.SubView.PlayScreen.Layout.QuestionBody
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun Game01PlayContent(
    quizUiState: Game01QUizUiState.Success,
    userAnswer: String = "",
    submitUserAnswer: () -> Unit = {},
    onUserAnswerChanged: (String) -> Unit = {},
    remainingTime: Int,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .systemBarsPadding()
                .imePadding()
                .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            Text(
                text =
                    stringResource(
                        id = R.string.remaing_time_format,
                        String.format("%02d : %02d", remainingTime / 60, remainingTime % 60),
                    ),
                color = Color.Black,
                style = Typography.displayLarge,
                fontSize = 40.sp,
                modifier =
                    Modifier
                        .padding(16.dp)
                        .align(Alignment.Center),
            )
        }
        QuestionBody(
            systemMessage = stringResource(id = R.string.question_message),
            quizScript = quizUiState.QuizDataSet.question,
        )
        AnswerBody(
            userAnswer = userAnswer,
            onUserAnswerChanged = onUserAnswerChanged,
            scrollToBottom = {
                coroutineScope.launch {
                    scrollState.animateScrollTo(scrollState.maxValue)
                }
            },
        )
        ButtonBody(
            remaingTime = remainingTime,
            timeLimit = Game01ViewModel.ROUND_TIME_LIMIT,
            submitUserAnswer = submitUserAnswer,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Game01PlayContentPreview() {
    MallangTheme {
        Game01PlayContent(
            quizUiState =
                Game01QUizUiState.Success(
                    Game01QuizData(
                        id = 1L,
                        question = "이것은 질문입니다. 이것은 질문입니다이것은 질문입니다.이것은 질문입니다.이것은 질문입니다.이것은 질문입니다.이것은 질문입니다.이것은 질문입니다.이것은 질문입니다.이것은 질문입니다..이것은 질문입니다.이것은 질문입니다.이것은 질문입니다.이것은 질문입니다.이것은 질문입니다.이것은 질문입니다.이것은 질문입니다.이것은 질문입니다.이것은 질문입니다.이것은 질문입니다.이것은 질문입니다.이것은 질문입니다.",
                        answer = "이것은 답변입니다.",
                        difficulty = 1,
                    ),
                ),
            remainingTime = 100,
        )
    }
}
