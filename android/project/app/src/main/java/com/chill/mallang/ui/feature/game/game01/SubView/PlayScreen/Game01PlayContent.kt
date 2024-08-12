package com.chill.mallang.ui.feature.game.game01.SubView.PlayScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.R
import com.chill.mallang.ui.feature.game.game01.Game01QUizUiState
import com.chill.mallang.ui.feature.game.game01.Game01ViewModel
import com.chill.mallang.ui.feature.game.game01.SubView.PlayScreen.Layout.AnswerBody
import com.chill.mallang.ui.feature.game.game01.SubView.PlayScreen.Layout.ButtonBody
import com.chill.mallang.ui.feature.game.game01.SubView.PlayScreen.Layout.QuestionBody
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography

@Composable
fun Game01PlayContent(
    quizUiState: Game01QUizUiState,
    userAnswer: String = "",
    submitUserAnswer: () -> Unit = {},
    onUserAnswerChanged: (String) -> Unit = {},
    remainingTime: Int,
    modifier: Modifier = Modifier,
) {
    when (quizUiState) {
        is Game01QUizUiState.Loading -> {
            CircularProgressIndicator()
        }

        is Game01QUizUiState.Success -> {
            Column(
                modifier =
                    modifier
                        .fillMaxWidth()
                        .imePadding(),
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

                LazyColumn(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .weight(1f),
                ) {
                    item {
                        QuestionBody(
                            systemMessage = stringResource(id = R.string.question_message),
                            quizScript = quizUiState.QuizDataSet.question,
                        )
                    }
                    item {
                        AnswerBody(
                            userAnswer = userAnswer,
                            onUserAnswerChanged = onUserAnswerChanged,
                        )
                    }
                    item {
                        ButtonBody(
                            remaingTime = remainingTime,
                            timeLimit = Game01ViewModel.ROUND_TIME_LIMIT,
                            submitUserAnswer = submitUserAnswer,
                        )
                    }
                }
            }
        }

        is Game01QUizUiState.Error -> {
            Text(text = quizUiState.errorMessage)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Game01PlayContentPreview() {
    MallangTheme {
        Game01PlayScreen()
    }
}
