package com.chill.mallang.ui.feature.game.game01

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.chill.mallang.R
import com.chill.mallang.ui.component.BackConfirmHandler
import com.chill.mallang.ui.feature.game.game01.Game01ViewModel.Game01Constants.ROUND_COUNT
import com.chill.mallang.ui.feature.game.game01.SubView.Game01CurtainCallScreen
import com.chill.mallang.ui.feature.game.game01.SubView.Game01LoadingScreen
import com.chill.mallang.ui.feature.game.game01.SubView.Game01PlayScreen
import com.chill.mallang.ui.feature.game.game01.SubView.ReviewScreen.Game01ReviewScreen
import com.chill.mallang.ui.feature.game.game01.SubView.Game01RewardScreen
import com.chill.mallang.ui.feature.game.game01.SubView.Game01RoundDoneScreen
import com.chill.mallang.ui.feature.game.game01.SubView.Game01RoundScreen
import com.chill.mallang.ui.feature.game.game01.SubView.Game01SplashScreen
import com.chill.mallang.ui.feature.game.game01.SubView.ResultScreen.Game01ResultScreen
import com.chill.mallang.ui.feature.topbar.TopbarHandler
import com.chill.mallang.ui.theme.MallangTheme
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun Game01Screen(
    areaId: Long = -1L,
    popUpBackStack: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val viewModel: Game01ViewModel = hiltViewModel()
    val (navController, setNavController) = remember { mutableStateOf<NavController?>(null) }
    val (isBackPressed, setBackPressed) = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.initializeAreaId(areaId)
    }

    BackConfirmHandler(
        isBackPressed = isBackPressed,
        onConfirmMessage = stringResource(id = R.string.positive_button_message),
        onConfirm = {
            setBackPressed(false)
            popUpBackStack()
        },
        onDismissMessage = stringResource(id = R.string.nagative_button_message),
        onDismiss = {
            setBackPressed(false)
        },
        title = stringResource(id = R.string.game_give_up_message),
    )
    BackHandler(onBack = { setBackPressed(true) })

    TopbarHandler(
        isVisible = true,
        title = stringResource(id = R.string.game_title),
        onBack = { nav ->
            setBackPressed(true)
            setNavController(nav)
        },
    )

    HandleGame01Event(
        game01UiEvent = viewModel.gameUiEvent,
        fetchQuizIdsInfo = { viewModel.fetchQuizIds() },
        fetchQuizData = { viewModel.fetchQuiz() },
        fetchFinalResult = { viewModel.fetchFinalResult() },
        fetchReviews = { viewModel.fetchReviews() },
        updateGame01StateToRoundLoad = { viewModel.updateGame01State(Game01State.ROUND_LOAD) },
        updateGame01StateToRoundReady = { viewModel.updateGame01State(Game01State.ROUND_READY) },
        updateGame01StateToReview = { viewModel.updateGame01State(Game01State.REVIEW) },
        getCurrentRound = { viewModel.getCurrentRound() },
        increaseRound = { viewModel.increaseRound() },
    )

    when (viewModel.game01State) {
        Game01State.INIT ->
            Game01SplashScreen()

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
                viewModel = viewModel,
                completeReview = { viewModel.updateGame01State(Game01State.FINISH) },
            )

        Game01State.FINISH ->
            Game01ResultScreen(
                viewModel = viewModel,
                completeCheckResult = { viewModel.updateGame01State(Game01State.REWARD)},
            )

        Game01State.REWARD ->
            Game01RewardScreen(
                completeReward = { viewModel.updateGame01State(Game01State.CURTAIN_CALL) },
            )

        Game01State.CURTAIN_CALL ->
            Game01CurtainCallScreen(
                finishGame = popUpBackStack,
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
    fetchReviews: () -> Unit,
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
                    fetchReviews()
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
