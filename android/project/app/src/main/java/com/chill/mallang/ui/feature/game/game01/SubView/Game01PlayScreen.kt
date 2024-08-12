package com.chill.mallang.ui.feature.game.game01.SubView

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chill.mallang.R
import com.chill.mallang.ui.component.LongBlackButtonWithTimer
import com.chill.mallang.ui.feature.game.game01.Game01QUizUiState
import com.chill.mallang.ui.feature.game.game01.Game01ViewModel
import com.chill.mallang.ui.theme.Gray3
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography

@Composable
fun Game01PlayScreen(
    userAnswer: String = "",
    submitUserAnswer: () -> Unit = {},
    onUserAnswerChanged: (String) -> Unit = {},
    viewModel: Game01ViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    val quizUiState by viewModel.QuizUiState.collectAsStateWithLifecycle()
    val remainingTime by viewModel.remainingTime.collectAsStateWithLifecycle()

    val view = LocalView.current
    ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
        insets
    }

    if (remainingTime == 0) {
        submitUserAnswer()
    }

    LaunchedEffect(Unit) {
        viewModel.startTimer()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Game01PlayContent(
            quizUiState = quizUiState,
            userAnswer = userAnswer,
            submitUserAnswer = submitUserAnswer,
            onUserAnswerChanged = onUserAnswerChanged,
            remainingTime = remainingTime,
        )
    }
}

@Composable
fun Game01PlayContent(
    quizUiState: Game01QUizUiState,
    userAnswer: String = "",
    submitUserAnswer: () -> Unit = {},
    onUserAnswerChanged: (String) -> Unit = {},
    viewModel: Game01ViewModel = viewModel(),
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
                            timeLimit = Game01ViewModel.Game01Constants.ROUND_TIME_LIMIT,
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

@Composable
fun QuestionBody(
    systemMessage: String,
    quizScript: String,
) {
    Box(
        modifier =
            Modifier
                .padding(12.dp)
                .border(width = 1.5.dp, color = Gray6, shape = RoundedCornerShape(10.dp))
                .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 30.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(id = R.string.question_message_format, systemMessage),
                    style = Typography.titleSmall,
                    fontSize = 20.sp,
                )
            }
            Box(modifier = Modifier.height(15.dp))
            Spacer(
                modifier =
                    Modifier
                        .height(2.dp)
                        .fillMaxWidth()
                        .background(Gray3),
            )
            Box(modifier = Modifier.height(15.dp))
            Text(
                text = quizScript,
                style = Typography.titleSmall,
                fontSize = 15.sp,
                lineHeight = 25.sp,
            )
        }
    }
}

@Composable
fun AnswerBody(
    userAnswer: String,
    onUserAnswerChanged: (String) -> Unit,
) {
    Box(
        modifier =
            Modifier
                .padding(12.dp)
                .border(width = 1.5.dp, color = Gray6, shape = RoundedCornerShape(10.dp))
                .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "Answer",
                    style = Typography.titleSmall,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                )
            }
            Box(modifier = Modifier.height(15.dp))
            Spacer(
                modifier =
                    Modifier
                        .height(2.dp)
                        .fillMaxWidth()
                        .background(Gray3),
            )
            Box(modifier = Modifier.height(15.dp))
            BasicTextField(
                value = userAnswer,
                onValueChange = {
                    onUserAnswerChanged(it)
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.White)
                        .padding(8.dp),
                textStyle =
                    TextStyle(
                        fontSize = 15.sp,
                        color = Color.Black,
                    ),
            )
        }
    }
}

@Composable
fun ButtonBody(
    remaingTime: Int,
    timeLimit: Int,
    submitUserAnswer: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ) {
        LongBlackButtonWithTimer(
            onClick = { submitUserAnswer() },
            buttonText = stringResource(id = R.string.submit_message),
            remainingTime = remaingTime,
            timeLimit = timeLimit,
            modifier = modifier.padding(horizontal = 20.dp),
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Game01PlayScreenPreview() {
    MallangTheme {
        Game01PlayScreen()
    }
}
