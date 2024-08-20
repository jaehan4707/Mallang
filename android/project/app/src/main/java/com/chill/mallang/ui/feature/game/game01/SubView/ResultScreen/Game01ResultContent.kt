package com.chill.mallang.ui.feature.game.game01.SubView.ResultScreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.chill.mallang.R
import com.chill.mallang.data.model.entity.Game01PlayResult
import com.chill.mallang.data.model.entity.Game01TeamPlayResult
import com.chill.mallang.data.model.entity.Game01UserPlayResult
import com.chill.mallang.data.model.entity.GameUserRecord
import com.chill.mallang.ui.component.LongBlackButton
import com.chill.mallang.ui.feature.game.game01.Dialog.GameWinDialog
import com.chill.mallang.ui.feature.game.game01.SubView.ResultScreen.Layout.LeaderBoardBody
import com.chill.mallang.ui.feature.game.game01.SubView.ResultScreen.Layout.ResultDetailBody
import com.chill.mallang.ui.feature.game.game01.SubView.ResultScreen.Layout.ResultImageBody
import com.chill.mallang.ui.feature.game.game01.SubView.ResultScreen.Layout.ResultTitleBody
import com.chill.mallang.ui.feature.game.game01.SubView.ResultScreen.Layout.ScoreBody
import com.chill.mallang.ui.theme.MallangTheme
import kotlinx.coroutines.delay

@Composable
fun Game01ResultContent(
    userName: String,
    userTeamId: Long,
    finalResult: Game01PlayResult,
    completeCheckResult: () -> Unit,
    playTotalPointSoundEffect: () -> Unit = {},
    playGameWinSoundEffect: () -> Unit = {},
    playGameLoseSoundEffect: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var isScoreBodyTransparent by remember { mutableStateOf(false) }
    var showTeamResult by remember { mutableStateOf(false) }
    var DialogVisibility by remember { mutableStateOf(false) }
    var isButtonVisible by remember { mutableStateOf(false) }

    val scoreBodyAlpha by animateFloatAsState(
        targetValue = if (isScoreBodyTransparent) 0.0f else 1.0f,
        animationSpec = tween(durationMillis = 500),
    )
    val buttonAlpha by animateFloatAsState(
        targetValue = if (isButtonVisible) 1.0f else 0.0f,
        animationSpec = tween(durationMillis = 500),
    )

    val isVictory =
        if (finalResult.teamPlayResult.myTeamTotalScore > finalResult.teamPlayResult.oppoTeamTotalScore) {
            true
        } else {
            false
        }

    LaunchedEffect(Unit) {
        playTotalPointSoundEffect()
        delay(2500L)
        isScoreBodyTransparent = true
        delay(1000L)
        if (isVictory) {
            playGameWinSoundEffect()
        } else {
            playGameLoseSoundEffect()
        }
        showTeamResult = true
        delay(2500L)
        if (finalResult.teamPlayResult.myTeamTotalScore > finalResult.teamPlayResult.oppoTeamTotalScore) {
            if ((finalResult.teamPlayResult.myTeamTotalScore - finalResult.userPlayResult.totalScore) <=
                finalResult.teamPlayResult.oppoTeamTotalScore
            ) {
                DialogVisibility = true
            }
        }
        delay(500L)
        isButtonVisible = true
    }

    if (DialogVisibility) {
        GameWinDialog(
            teamId = userTeamId,
            onDismiss = { DialogVisibility = false },
        )
    }

    Box(
        modifier =
            modifier
                .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (!showTeamResult) {
            ScoreBody(
                totalScore = finalResult.userPlayResult.totalScore,
                modifier.graphicsLayer(alpha = scoreBodyAlpha),
            )
        } else {
            Column {
                ResultTitleBody(
                    isVictory = isVictory,
                )
                ResultDetailBody(
                    myTeamScore = finalResult.teamPlayResult.myTeamTotalScore,
                    oppoTeamScore = finalResult.teamPlayResult.oppoTeamTotalScore,
                )
                ResultImageBody(
                    isVictory = isVictory,
                    teamId = userTeamId,
                )
                LeaderBoardBody(
                    userName = userName,
                    userScore = finalResult.userPlayResult.totalScore,
                    leaderList = finalResult.teamPlayResult.myTeamRankList,
                    userTeamId = userTeamId,
                )
            }
            LongBlackButton(
                modifier =
                    modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 10.dp)
                        .align(Alignment.BottomCenter)
                        .graphicsLayer(alpha = buttonAlpha),
                onClick = {
                    completeCheckResult()
                },
                text = stringResource(id = R.string.game_confirm),
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Game01ResultContentPreview() {
    MallangTheme {
        Game01ResultContent(
            userName = "사람01",
            completeCheckResult = {},
            userTeamId = 2L,
            finalResult =
                Game01PlayResult(
                    userPlayResult =
                        Game01UserPlayResult(
                            score = listOf(),
                            totalScore = 40F,
                        ),
                    teamPlayResult =
                        Game01TeamPlayResult(
                            myTeamRankList =
                                listOf(
                                    GameUserRecord("사람01", 100F),
                                    GameUserRecord("사람01", 90F),
                                    GameUserRecord("사람01", 80F),
                                    GameUserRecord("사람01", 70F),
                                    GameUserRecord("사람01", 60F),
                                    GameUserRecord("사람01", 50F),
                                    GameUserRecord("사람01", 40F),
                                    GameUserRecord("사람01", 30F),
                                    GameUserRecord("사람01", 20F),
                                ),
                            myTeamTotalScore = 200F,
                            oppoTeamTotalScore = 170F,
                        ),
                ),
        )
    }
}
