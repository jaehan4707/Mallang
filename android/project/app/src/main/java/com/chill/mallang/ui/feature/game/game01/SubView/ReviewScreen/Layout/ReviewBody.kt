package com.chill.mallang.ui.feature.game.game01.SubView.ReviewScreen.Layout

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.R
import com.chill.mallang.data.model.entity.Game01PlayResult
import com.chill.mallang.data.model.entity.Game01TeamPlayResult
import com.chill.mallang.data.model.entity.Game01UserPlayResult
import com.chill.mallang.ui.component.LongBlackButton
import com.chill.mallang.ui.feature.game.game01.Game01ReviewUiState
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography
import kotlinx.coroutines.delay

@Composable
fun ReviewBody(
    reviewUiState: Game01ReviewUiState.Success,
    completeReview: () -> Unit,
    showDialog: () -> Unit,
    playPointIndicatorSoundEffect: () -> Unit = {},
    playGradeStampSoundEffect: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isTransparent by remember { mutableStateOf(false) }
    var isRoundTransparent by remember { mutableStateOf(false) }
    var isGradeStampBoxExpanded by remember { mutableStateOf(false) }
    var isFeedbackTransparent by remember { mutableStateOf(false) }
    var isButtonTransparent by remember { mutableStateOf(false) }

    val size by animateDpAsState(
        targetValue = if (isExpanded) 170.dp else 200.dp,
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

    val gradeStampBoxSize by animateDpAsState(
        targetValue = if (isGradeStampBoxExpanded) 200.dp else 80.dp,
        animationSpec = tween(durationMillis = 1000),
    )

    LaunchedEffect(Unit) {
        delay(500L)
        isRoundTransparent = true
        delay(2500L)
        isGradeStampBoxExpanded = true
        delay(1000L)
        playGradeStampSoundEffect()
        isExpanded = true
        isTransparent = true
        delay(500L)
        isFeedbackTransparent = true
        delay(500L)
        isButtonTransparent = true
    }

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
            StampBody(
                modifier = Modifier
                    .size(gradeStampBoxSize),
                size = size,
                alpha = alpha,
                totalPoint = reviewUiState.finalResult.userPlayResult.totalScore,
            )

            Row(
                modifier = Modifier.graphicsLayer(roundAlpha),
            ) {
                reviewUiState.finalResult.userPlayResult.score.forEachIndexed { index, score ->
                    RoundResultItem(
                        round = index + 1,
                        modifier = modifier.weight(1F),
                        score = score.toInt(),
                        delay = (index + 1) * 500,
                        playPointIndicatorSoundEffect = playPointIndicatorSoundEffect,
                    )
                }
            }
            FeedbackBody(
                totalPoint = reviewUiState.finalResult.userPlayResult.totalScore,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .graphicsLayer(alpha = feedbackAlpha),
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .graphicsLayer(alpha = buttonAlpha),
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

@Preview(showBackground = true)
@Composable
fun ReviewBodyPreview() {
    MallangTheme {
        ReviewBody(
            reviewUiState =
                Game01ReviewUiState.Success(
                    finalResult =
                        Game01PlayResult(
                            userPlayResult =
                                Game01UserPlayResult(
                                    score = listOf(100F, 100F, 100F),
                                    totalScore = 300F,
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
            showDialog = {},
        )
    }
}
