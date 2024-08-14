package com.chill.mallang.ui.feature.game.game01.SubView.PlayScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chill.mallang.ui.feature.game.game01.Game01QUizUiState
import com.chill.mallang.ui.feature.game.game01.Game01ViewModel
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun Game01PlayScreen(
    userAnswer: String = "",
    submitUserAnswer: () -> Unit = {},
    onUserAnswerChanged: (String) -> Unit = {},
    viewModel: Game01ViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {
    val quizUiState by viewModel.QuizUiState.collectAsStateWithLifecycle()
    val remainingTime by viewModel.remainingTime.collectAsStateWithLifecycle()

    if (remainingTime == 0) {
        submitUserAnswer()
    }

    LaunchedEffect(Unit) {
        viewModel.startTimer()
    }

    when (quizUiState) {
        is Game01QUizUiState.Success -> {
            Game01PlayContent(
                quizUiState = quizUiState as Game01QUizUiState.Success,
                userAnswer = userAnswer,
                submitUserAnswer = submitUserAnswer,
                onUserAnswerChanged = onUserAnswerChanged,
                remainingTime = remainingTime,
                modifier = modifier,
            )
        }

        is Game01QUizUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter,
            ) {
                Text(
                    text = (quizUiState as Game01QUizUiState.Error).errorMessage,
                )
            }
        }

        is Game01QUizUiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Game01PlayScreenPreview() {
    MallangTheme {
        Game01PlayScreen()
    }
}
