package com.chill.mallang.ui.feature.game.game01.SubView

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chill.mallang.R
import com.chill.mallang.ui.component.LongBlackButton
import com.chill.mallang.ui.feature.game.game01.Dialog.GameReviewDialog
import com.chill.mallang.ui.feature.game.game01.Dialog.RoundResult
import com.chill.mallang.ui.feature.game.game01.Game01ReviewUiState
import com.chill.mallang.ui.feature.game.game01.Game01ViewModel
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.QuokkaLightBrown
import com.chill.mallang.ui.theme.QuokkaRealBrown
import com.chill.mallang.ui.theme.SuperLightGray
import com.chill.mallang.ui.theme.Typography
import kotlinx.coroutines.delay

@Composable
fun Game01ReviewScreen(
    viewModel: Game01ViewModel = viewModel(),
    completeReview: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val reviewUiState by viewModel.reviewUiState.collectAsStateWithLifecycle()
    var DialogVisibility by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Game01ReviewContent(
            reviewUiState = reviewUiState,
            showDialog = { DialogVisibility = true },
            completeReview = completeReview,
            modifier = modifier,
        )
    }

    val roundResultList = (1..3).map { i ->
        RoundResult(
            round = i,
            quizDataSet = viewModel.questionDataSetList[i - 1],
            userAnswer = viewModel.userAnswerList[i],
            userScore = 100
        )
    }

    if(DialogVisibility) {
        GameReviewDialog(
            index = 1,
            roundResults = roundResultList,
            onDismiss = { DialogVisibility = false },
        )
    }
}

@Composable
fun Game01ReviewContent(
    reviewUiState: Game01ReviewUiState,
    showDialog: () -> Unit,
    completeReview: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (reviewUiState) {
        is Game01ReviewUiState.Loading -> {
            CircularProgressIndicator()
        }

        is Game01ReviewUiState.Success -> {
            ReviewBody(
                reviewUiState = reviewUiState,
                showDialog = showDialog,
                completeReview = completeReview,
            )
        }

        is Game01ReviewUiState.Error -> {
            Text(text = reviewUiState.errorMessage)
        }
    }
}

@Composable
fun ReviewBody(
    reviewUiState: Game01ReviewUiState.Success,
    completeReview: () -> Unit,
    showDialog: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isTransparent by remember { mutableStateOf(false) }
    var isRoundTransparent by remember { mutableStateOf(false) }
    var isGradeStampBoxExpanded by remember { mutableStateOf(false) }
    var isFeedbackTransparent by remember { mutableStateOf(false) }
    var isButtonTransparent by remember { mutableStateOf(false) }

    val size by animateDpAsState(
        targetValue = if (isExpanded) 250.dp else 300.dp,
        animationSpec = tween(durationMillis = 300),
    )

    val alpha by animateFloatAsState(
        targetValue = if (isTransparent) 1.0f else 0.0f,
        animationSpec = tween(durationMillis = 300),
    )

    val roundAlpha by animateFloatAsState(
        targetValue = if (isRoundTransparent) 1.0f else 0.0f,
        animationSpec = tween(durationMillis = 300),
    )

    val feedbackAlpha by animateFloatAsState(
        targetValue = if (isFeedbackTransparent) 1.0f else 0.0f,
        animationSpec = tween(durationMillis = 300),
    )

    val buttonAlpha by animateFloatAsState(
        targetValue = if (isButtonTransparent) 1.0f else 0.0f,
        animationSpec = tween(durationMillis = 500),
    )

    LaunchedEffect(Unit) {
        delay(500L)
        isRoundTransparent = true
        delay(4000L)
        isGradeStampBoxExpanded = true
        delay(1500L)
        isExpanded = true
        isTransparent = true
        delay(1000L)
        isFeedbackTransparent = true
        delay(1000L)
        isButtonTransparent = true
    }

    val gradeStampBoxSize by animateDpAsState(
        targetValue = if (isGradeStampBoxExpanded) 300.dp else 100.dp,
        animationSpec = tween(durationMillis = 1000),
    )

    Box(
        modifier =
        modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.game_result_title),
                style = Typography.displayLarge,
                fontSize = 40.sp,
                modifier = modifier.padding(vertical = 10.dp),
            )
            Box(
                modifier = Modifier.size(gradeStampBoxSize),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_result_a_plus),
                    contentDescription = "",
                    modifier =
                    Modifier
                        .size(size)
                        .graphicsLayer(alpha = alpha),
                )
            }

            Row(
                modifier = Modifier.graphicsLayer(roundAlpha),
            ) {
                reviewUiState.finalResult.userPlayResult.score.forEachIndexed { index, score ->
                    RoundResultItem(
                        round = index + 1,
                        modifier = modifier.weight(1F),
                        score = score.toInt(),
                        delay = (index + 1) * 1000,
                    )
                }
            }
            RoundResultBox(
                modifier = Modifier.graphicsLayer(alpha = feedbackAlpha),
            )

            Row(
                modifier = Modifier.graphicsLayer(alpha = buttonAlpha),
            ) {
                LongBlackButton(
                    onClick = { showDialog() },
                    text = stringResource(id = R.string.show_game_result_detail_message),
                    modifier =
                    Modifier
                        .weight(1F)
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                )
                LongBlackButton(
                    onClick = { completeReview() },
                    text = stringResource(id = R.string.game_confirm),
                    modifier =
                    Modifier
                        .weight(1F)
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                )
            }
        }
    }
}

@Composable
fun RoundResultItem(
    round: Int = 0,
    score: Int = 0,
    delay: Int = 0,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
        modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 10.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = stringResource(id = R.string.round_title_format, round),
                style = Typography.displayLarge,
                fontSize = 30.sp,
            )
            ScoreProgressIndicator(
                score = score,
                perfectScore = 100,
                delay = delay,
            )
        }
    }
}

@Composable
fun ScoreProgressIndicator(
    score: Int = 0,
    perfectScore: Int = 100,
    strokeWidth: Float = 5f,
    delay: Int = 1000,
    modifier: Modifier = Modifier,
) {
    val animatedProgress = remember { Animatable(0f) }
    val targetProgress = (score.toFloat() / perfectScore)

    var isTransparent by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (isTransparent) 1.0f else 0.0f,
        animationSpec = tween(durationMillis = 300),
    )

    LaunchedEffect(targetProgress) {
        delay(delay.toLong())
        isTransparent = true
        animatedProgress.animateTo(
            targetProgress,
            animationSpec = tween(durationMillis = 1500),
        )
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier =
        modifier
            .size(100.dp)
            .padding(10.dp)
            .graphicsLayer(alpha = alpha),
    ) {
        CircularProgressIndicator(
            progress = { animatedProgress.value },
            modifier = Modifier.fillMaxSize(),
            strokeWidth = strokeWidth.dp,
            strokeCap = StrokeCap.Round,
            color = QuokkaRealBrown,
            trackColor = SuperLightGray,
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center),
        ) {
            Text(
                text = "${score}점",
                color = QuokkaRealBrown,
                style = Typography.displayLarge,
                fontSize = 30.sp,
            )
            Text(
                text = "/${perfectScore}점",
                style = Typography.displayLarge,
                fontSize = 16.sp,
            )
        }
    }
}

@Composable
fun RoundResultBox(modifier: Modifier = Modifier) {
    var isTransparent by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (isTransparent) 1.0f else 0.0f,
        animationSpec = tween(durationMillis = 300),
    )

    LaunchedEffect(Unit) {
        delay(5000L)
        isTransparent = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier =
        modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .background(color = QuokkaLightBrown, shape = RoundedCornerShape(16.dp))
            .border(1.dp, Color.Black, shape = RoundedCornerShape(16.dp)),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(
                modifier = Modifier.height(50.dp),
            )
            Text(
                text = "\" 용사비등(龍蛇飛騰) 이시옵니다! \"",
                fontSize = 20.sp,
                modifier = Modifier.graphicsLayer(alpha = alpha),
            )
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 5.dp),
            ) {
                Image(
                    modifier =
                        modifier
                            .size(70.dp)
                            .align(Alignment.CenterEnd),
                    painter = painterResource(id = R.drawable.img_grader),
                    contentDescription = "",
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Game01ReviewScreenPreview() {
    MallangTheme {
        Game01ReviewScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun RoundResultItemPreview() {
    MallangTheme {
        RoundResultItem()
    }
}

@Preview(showBackground = true)
@Composable
fun RoundResultBoxPreview() {
    MallangTheme {
        RoundResultBox()
    }
}
