package com.chill.mallang.ui.feature.game.game01.SubView.ResultScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chill.mallang.data.model.entity.Game01PlayResult
import com.chill.mallang.data.model.entity.Game01TeamPlayResult
import com.chill.mallang.data.model.entity.Game01UserPlayResult
import com.chill.mallang.data.model.entity.GameUserRecord
import com.chill.mallang.ui.feature.game.game01.Game01ViewModel
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun Game01ResultScreen(
    viewModel: Game01ViewModel = viewModel(),
    completeCheckResult: () -> Unit = {},
    playTotalPointSoundEffect: () -> Unit = {},
    playGameWinSoundEffect: () -> Unit = {},
    playGameLoseSoundEffect: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Game01ResultContent(
        userName = viewModel.userInfo.nickName,
        finalResult = viewModel.playResult,
        completeCheckResult = completeCheckResult,
        userTeamId = viewModel.userInfo.factionId,
        playTotalPointSoundEffect = playTotalPointSoundEffect,
        playGameWinSoundEffect = playGameWinSoundEffect,
        playGameLoseSoundEffect = playGameLoseSoundEffect,
        modifier = modifier,
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Game01ResultScreenPreview() {
    MallangTheme {
        Game01ResultContent(
            finalResult =
                Game01PlayResult(
                    userPlayResult =
                        Game01UserPlayResult(
                            score = listOf(),
                            totalScore = 90F,
                        ),
                    teamPlayResult =
                        Game01TeamPlayResult(
                            myTeamRankList =
                                listOf(
                                    GameUserRecord("사람01", 100F),
                                    GameUserRecord("사람01", 100F),
                                    GameUserRecord("사람01", 100F),
                                    GameUserRecord("사람01", 100F),
                                    GameUserRecord("사람01", 90F),
                                    GameUserRecord("사람01", 80F),
                                    GameUserRecord("사람01", 70F),
                                    GameUserRecord("사람01", 60F),
                                    GameUserRecord("사람01", 50F),
                                ),
                            myTeamTotalScore = 100F,
                            oppoTeamTotalScore = 0F,
                        ),
                ),
            userName = "사람01",
            completeCheckResult = {},
            userTeamId = 1L,
        )
    }
}
