package com.chill.mallang.ui.feature.game.game01.SubView.ReviewScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chill.mallang.data.model.entity.Game01PlayResult
import com.chill.mallang.data.model.entity.Game01QuizData
import com.chill.mallang.data.model.entity.Game01TeamPlayResult
import com.chill.mallang.data.model.entity.Game01UserPlayResult
import com.chill.mallang.ui.feature.game.game01.Dialog.RoundResult
import com.chill.mallang.ui.feature.game.game01.Game01ReviewUiState
import com.chill.mallang.ui.feature.game.game01.Game01ViewModel
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun Game01ReviewScreen(
    viewModel: Game01ViewModel = viewModel(),
    completeReview: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val reviewUiState by viewModel.reviewUiState.collectAsStateWithLifecycle()

    when (reviewUiState) {
        is Game01ReviewUiState.Success -> {
            val roundResultList =
                (1..3).map { i ->
                    RoundResult(
                        round = i,
                        quizDataSet = viewModel.questionDataSetList[i - 1],
                        userAnswer = viewModel.userAnswerList[i],
                        userScore =
                            (reviewUiState as Game01ReviewUiState.Success)
                                .finalResult.userPlayResult.score[i - 1]
                                .toInt(),
                    )
                }

            Game01ReviewContent(
                reviewUiState = reviewUiState as Game01ReviewUiState.Success,
                completeReview = completeReview,
                roundResultList = roundResultList,
                modifier = modifier,
            )
        }

        is Game01ReviewUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = (reviewUiState as Game01ReviewUiState.Error).errorMessage,
                )
            }
        }

        is Game01ReviewUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(50.dp))
                    Text(text = "결산중...")
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Game01ReviewScreenPreview() {
    MallangTheme {
        Game01ReviewContent(
            reviewUiState =
                Game01ReviewUiState.Success(
                    finalResult =
                        Game01PlayResult(
                            userPlayResult =
                                Game01UserPlayResult(
                                    score = listOf(40F, 40F, 40F),
                                    totalScore = 120F,
                                ),
                            teamPlayResult =
                                Game01TeamPlayResult(
                                    myTeamRankList = listOf(),
                                    myTeamTotalScore = 100F,
                                    oppoTeamTotalScore = 0F,
                                ),
                        ),
                    userAnswerList =
                        listOf(
                            "1",
                            "2",
                            "3",
                        ),
                ),
            completeReview = {},
            roundResultList =
                listOf(
                    RoundResult(
                        round = 1,
                        quizDataSet =
                            Game01QuizData(
                                id = 1L,
                                question = "11",
                                answer = "1",
                                difficulty = 1,
                            ),
                        userAnswer = "1",
                        userScore = 40,
                    ),
                    RoundResult(
                        round = 2,
                        quizDataSet =
                            Game01QuizData(
                                id = 2L,
                                question = "22",
                                answer = "2",
                                difficulty = 2,
                            ),
                        userAnswer = "2",
                        userScore = 40,
                    ),
                    RoundResult(
                        round = 3,
                        quizDataSet =
                            Game01QuizData(
                                id = 3L,
                                question = "33",
                                answer = "3",
                                difficulty = 3,
                            ),
                        userAnswer = "3",
                        userScore = 40,
                    ),
                ),
        )
    }
}
