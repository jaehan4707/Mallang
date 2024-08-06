package com.chill.mallang.ui.feature.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel
    @Inject
    constructor() : ViewModel() {
        var gameType by mutableStateOf(GameType.INIT)
            private set

        var gameState by mutableStateOf(GameState.INIT)
            private set

        fun initGameType(gameType: GameType) {
            this.gameType = gameType
        }

        fun updateGameState(gameState: GameState) {
            this.gameState = gameState
        }
    }

enum class GameState {
    INIT,
    READY,
    RUNNING,
    DONE,
}

enum class GameType {
    INIT,
    GAME01,
    GAME02,
    GAME03,
}
