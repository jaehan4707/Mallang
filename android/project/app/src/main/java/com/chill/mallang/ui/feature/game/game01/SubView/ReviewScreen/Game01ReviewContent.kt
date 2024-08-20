package com.chill.mallang.ui.feature.game.game01.SubView.ReviewScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chill.mallang.data.model.entity.Game01PlayResult
import com.chill.mallang.data.model.entity.Game01QuizData
import com.chill.mallang.data.model.entity.Game01TeamPlayResult
import com.chill.mallang.data.model.entity.Game01UserPlayResult
import com.chill.mallang.ui.feature.game.game01.Dialog.GameReviewDialog
import com.chill.mallang.ui.feature.game.game01.Dialog.RoundResult
import com.chill.mallang.ui.feature.game.game01.Game01ReviewUiState
import com.chill.mallang.ui.feature.game.game01.SubView.ReviewScreen.Layout.ReviewBody
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun Game01ReviewContent(
    reviewUiState: Game01ReviewUiState.Success,
    completeReview: () -> Unit,
    playPointIndicatorSoundEffect: () -> Unit = {},
    playGradeStampSoundEffect: () -> Unit = {},
    roundResultList: List<RoundResult>,
    modifier: Modifier = Modifier,
) {
    var DialogVisibility by remember { mutableStateOf(false) }

    ReviewBody(
        reviewUiState = reviewUiState,
        showDialog = { DialogVisibility = true },
        playPointIndicatorSoundEffect = playPointIndicatorSoundEffect,
        playGradeStampSoundEffect = playGradeStampSoundEffect,
        completeReview = completeReview,
    )

    if (DialogVisibility) {
        GameReviewDialog(
            index = 0,
            roundResults = roundResultList,
            onDismiss = { DialogVisibility = false },
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Game01ReviewContentPreview() {
    MallangTheme {
        Game01ReviewContent(
            reviewUiState =
                Game01ReviewUiState.Success(
                    finalResult =
                        Game01PlayResult(
                            userPlayResult =
                                Game01UserPlayResult(
                                    score = listOf(60F, 60F, 60F),
                                    totalScore =180F,
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
                        userScore = 60,
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
                        userScore = 60,
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
                        userScore = 60,
                    ),
                ),
        )
    }
}
