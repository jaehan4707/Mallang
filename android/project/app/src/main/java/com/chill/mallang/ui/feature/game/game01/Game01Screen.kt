package com.chill.mallang.ui.feature.game.game01

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.chill.mallang.R
import com.chill.mallang.ui.feature.game.game01.Game01ViewModel.Game01Constants.ROUND_COUNT
import com.chill.mallang.ui.feature.game.game01.SubView.Game01LoadingScreen
import com.chill.mallang.ui.feature.game.game01.SubView.Game01PlayScreen
import com.chill.mallang.ui.feature.game.game01.SubView.Game01ResultScreen
import com.chill.mallang.ui.feature.game.game01.SubView.Game01ReviewScreen
import com.chill.mallang.ui.feature.game.game01.SubView.Game01RoundDoneScreen
import com.chill.mallang.ui.feature.game.game01.SubView.Game01RoundScreen
import com.chill.mallang.ui.theme.MallangTheme
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun Game01Screen(modifier: Modifier = Modifier) {
    val viewModel: Game01ViewModel = hiltViewModel()

    HandleGame01Event(
        game01UiEvent = viewModel.gameUiEvent,
        fetchQuizIdsInfo = { viewModel.fetchQuizIds() },
        fetchQuizData = { viewModel.fetchQuiz() },
        fetchFinalResult = { viewModel.fetchFinalResult() },
        updateGame01StateToRoundLoad = { viewModel.updateGame01State(Game01State.ROUND_LOAD) },
        updateGame01StateToRoundReady = { viewModel.updateGame01State(Game01State.ROUND_READY) },
        updateGame01StateToReview = { viewModel.updateGame01State(Game01State.REVIEW) },
        getCurrentRound = { viewModel.getCurrentRound() },
        increaseRound = { viewModel.increaseRound() },
    )

    when (viewModel.game01State) {
        Game01State.INIT ->
            Game01LoadingScreen(
                loadingMessage = stringResource(id = R.string.loading_message_init),
            )

        Game01State.ROUND_LOAD ->
            Game01LoadingScreen(
                loadingMessage = stringResource(id = R.string.loading_message_round_load),
            )

        Game01State.ROUND_READY ->
            Game01RoundScreen(
                round = viewModel.gameRound,
                completeRoundLoad = { viewModel.completeRoundLoad() },
            )

        Game01State.ROUND_RUNNING ->
            Game01PlayScreen(
                submitUserAnswer = { viewModel.postUserAnswer() },
                onUserAnswerChanged = { viewModel.updateUserAnswer(viewModel.gameRound, it) },
                userAnswer = viewModel.userAnswerList[viewModel.gameRound],
                viewModel = viewModel,
            )

        Game01State.ROUND_SUBMIT ->
            Game01LoadingScreen(
                loadingMessage = stringResource(id = R.string.loading_message_done),
            )

        Game01State.ROUND_DONE ->
            Game01RoundDoneScreen()

        Game01State.REVIEW ->
            Game01ReviewScreen(
                completeReview = { viewModel.updateGame01State(Game01State.FINISH) },
            )

        Game01State.FINISH ->
            Game01ResultScreen(
                viewModel = viewModel,
            )
    }
}

@Composable
fun HandleGame01Event(
    game01UiEvent: SharedFlow<Game01UiEvent>,
    updateGame01StateToRoundLoad: () -> Unit,
    updateGame01StateToRoundReady: () -> Unit,
    updateGame01StateToReview: () -> Unit,
    fetchQuizIdsInfo: () -> Unit,
    fetchQuizData: () -> Unit,
    fetchFinalResult: () -> Unit,
    getCurrentRound: () -> Int,
    increaseRound: () -> Unit,
) {
    LaunchedEffect(game01UiEvent) {
        game01UiEvent.collectLatest { event ->
            when (event) {
                Game01UiEvent.CompleteUserInfoLoad -> {
                    fetchQuizIdsInfo()
                }

                Game01UiEvent.CompleteQuizIdsLoad -> {
                    updateGame01StateToRoundLoad()
                    fetchQuizData()
                }

                Game01UiEvent.CompleteQuizLoad -> {
                    updateGame01StateToRoundReady()
                }

                Game01UiEvent.CompletePostUserAnswer -> {
                    val currentRound = getCurrentRound()

                    if (currentRound == ROUND_COUNT) {
                        fetchFinalResult()
                    } else {
                        increaseRound()
                        fetchQuizData()
                        updateGame01StateToRoundLoad()
                    }
                }

                Game01UiEvent.CompleteGameResultLoad -> {
                    updateGame01StateToReview()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Game01ScreenPreview() {
    MallangTheme {
        Game01Screen()
    }
}
