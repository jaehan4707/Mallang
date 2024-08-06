package com.chill.mallang.ui.feature.game.game01

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.chill.mallang.ui.feature.fort_detail.UserRecord

@Stable
sealed interface Game01LoadQUizsUiState {
    data object Loading : Game01LoadQUizsUiState

    data class Success(
        val successMessage: String,
    ) : Game01LoadQUizsUiState

    data class Error(
        val errorMessage: String,
    ) : Game01LoadQUizsUiState
}

@Stable
sealed interface Game01FinalResultUiState {
    @Immutable
    data object Loading : Game01FinalResultUiState

    @Immutable
    data class Success(
        val finalResults: PlayResult,
    ) : Game01FinalResultUiState

    @Immutable
    data class Error(
        val errorMessage: String,
    ) : Game01FinalResultUiState
}

data class QuestionDataSet(
    val question: String,
    val answer: String,
    val difficulty: Int,
    val type: Int,
)

@Stable
data class PlayResult(
    val userPlayResult: UserPlayResult,
    val teamPlayResult: TeamPlayResult,
)

data class UserPlayResult(
    val rank: Int,
    val score: List<Int>,
    val totalScore: Int,
)

data class TeamPlayResult(
    val myTeamRankList: List<UserRecord>,
    val myTeamTotalScore: Int,
    val oppoTeamTotalScore: Int,
)
