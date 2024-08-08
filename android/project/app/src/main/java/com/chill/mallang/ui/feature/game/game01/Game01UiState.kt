package com.chill.mallang.ui.feature.game.game01

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.chill.mallang.data.model.entity.Game01PlayResult
import com.chill.mallang.data.model.entity.Game01QuizData

@Stable
sealed interface Game01QUizUiState {
    data object Loading : Game01QUizUiState

    data class Success(
        val QuizDataSet: Game01QuizData,
    ) : Game01QUizUiState

    data class Error(
        val errorMessage: String,
    ) : Game01QUizUiState
}

@Stable
sealed interface Game01FinalResultUiState {
    @Immutable
    data object Loading : Game01FinalResultUiState

    @Immutable
    data class Success(
        val finalResult: Game01PlayResult,
    ) : Game01FinalResultUiState

    @Immutable
    data class Error(
        val errorMessage: String,
    ) : Game01FinalResultUiState
}
