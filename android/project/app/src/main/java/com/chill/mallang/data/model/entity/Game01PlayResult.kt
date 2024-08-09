package com.chill.mallang.data.model.entity

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class Game01PlayResult(
    @SerialName("User")
    val userPlayResult: Game01UserPlayResult,
    @SerialName("Team")
    val teamPlayResult: Game01TeamPlayResult,
)

@Serializable
@Immutable
data class Game01UserPlayResult(
    @SerialName("Score")
    val score: List<Float>,
    @SerialName("Total Score")
    val totalScore: Float,
)

@Serializable
@Immutable
data class Game01TeamPlayResult(
    @SerialName("My Team Rank")
    val myTeamRankList: List<GameUserRecord>,
    @SerialName("My Team Total Score")
    val myTeamTotalScore: Float,
    @SerialName("Oppo Team Total Score")
    val oppoTeamTotalScore: Float,
)
