package com.chill.mallang.ui.feature.game.game01.SubView.ResultScreen

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chill.mallang.data.model.entity.Game01PlayResult
import com.chill.mallang.data.model.entity.Game01TeamPlayResult
import com.chill.mallang.data.model.entity.Game01UserPlayResult
import com.chill.mallang.data.model.entity.GameUserRecord
import com.chill.mallang.ui.feature.game.game01.Game01FinalResultUiState
import com.chill.mallang.ui.feature.game.game01.Game01ViewModel
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun Game01ResultScreen(
    viewModel: Game01ViewModel = viewModel(),
    completeCheckResult: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val resultUiState by viewModel.resultUiState.collectAsStateWithLifecycle()

    when (resultUiState) {
        is Game01FinalResultUiState.Success -> {
            Game01ResultContent(
                userName = viewModel.userInfo.nickName,
                resultUiState = resultUiState as Game01FinalResultUiState.Success,
                completeCheckResult = completeCheckResult,
                userTeamId = viewModel.userInfo.factionId,
            )
        }

        is Game01FinalResultUiState.Error -> {
            Text(text = (resultUiState as Game01FinalResultUiState.Error).errorMessage)
        }

        is Game01FinalResultUiState.Loading -> {
            CircularProgressIndicator()
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Game01ResultScreenPreview() {
    MallangTheme {
        Game01ResultContent(
            resultUiState =
                Game01FinalResultUiState.Success(
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
                ),
            userName = "사람01",
            completeCheckResult = {},
            userTeamId = 1L,
        )
    }
}
