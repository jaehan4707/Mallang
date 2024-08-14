package com.chill.mallang.ui.feature.game

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.chill.mallang.ui.feature.game.game01.Game01Screen
import com.chill.mallang.ui.feature.game.game02.Game02Screen
import com.chill.mallang.ui.feature.game.game03.Game03Screen
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun GameScreen(
    gameType: GameType,
    modifier: Modifier = Modifier,
) {
    val viewModel: GameViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.initGameType(gameType)
        viewModel.updateGameState(gameState = GameState.READY)
    }

    when (viewModel.gameType) {
        GameType.INIT -> TODO()
        GameType.GAME01 -> Game01Screen()
        GameType.GAME02 -> Game02Screen()
        GameType.GAME03 -> Game03Screen()
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun GameLobbyScreenPreview() {
    MallangTheme {
        GameScreen(GameType.GAME01)
    }
}
