package com.chill.mallang.ui.feature.game.game01

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.chill.mallang.R
import com.chill.mallang.ui.feature.game.game01.SubView.Game01LoadingScreen
import com.chill.mallang.ui.feature.game.game01.SubView.Game01PlayScreen
import com.chill.mallang.ui.feature.game.game01.SubView.Game01ResultScreen
import com.chill.mallang.ui.feature.game.game01.SubView.Game01RoundDoneScreen
import com.chill.mallang.ui.feature.game.game01.SubView.Game01RoundScreen
import com.chill.mallang.ui.theme.MallangTheme
import kotlinx.coroutines.delay

@Composable
fun Game01Screen(modifier: Modifier = Modifier) {
    val viewModel: Game01ViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        delay(1000L)
        viewModel.startGame01()
    }

    when (viewModel.game01State) {
        Game01State.INIT ->
            Game01LoadingScreen(
                loadingMessage = stringResource(id = R.string.loading_message_init)
            )
        Game01State.ROUND_READY ->
            Game01RoundScreen(
                round = viewModel.gameRound,
                completeRoundLoad = { viewModel.completeRoundLoad() }
            )
        Game01State.ROUND_RUNNING ->
            Game01PlayScreen(
                submitUserAnswer = { viewModel.postUserAnswer() },
                onUserAnswerChanged = { viewModel.updateUserAnswer(viewModel.gameRound, it) },
                userAnswer = viewModel.userAnswerList[viewModel.gameRound],
                round = viewModel.gameRound,
                roundQuesetion = viewModel.questionDataSetList[viewModel.gameRound - 1].question,
                viewModel = viewModel
            )
        Game01State.ROUND_SUBMIT ->
            Game01LoadingScreen(
                loadingMessage = stringResource(id = R.string.loading_message_done)
            )
        Game01State.ROUND_DONE ->
            Game01RoundDoneScreen()
        Game01State.FINISH ->
            Game01ResultScreen(
                viewModel = viewModel,
            )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Game01ScreenPreview() {
    MallangTheme {
        Game01Screen()
    }
}
