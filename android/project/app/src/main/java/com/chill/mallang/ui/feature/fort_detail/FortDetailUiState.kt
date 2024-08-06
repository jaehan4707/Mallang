 package com.chill.mallang.ui.feature.fort_detail

import androidx.compose.runtime.Stable
import com.chill.mallang.data.model.entity.GameUserRecord

 @Stable
sealed interface OccupationState {
    data object Loading : OccupationState

    data class Success(
        val areaName: String,
        val myTeamInfo: TeamInfo,
        val oppoTeamInfo: TeamInfo,
    ) : OccupationState

    data class Error(
        val errorMessage: String,
    ) : OccupationState
}

@Stable
sealed interface TeamLeadersState {
    data object Loading : TeamLeadersState

    data class Success(
        val userRecord: GameUserRecord,
        val myTeamRecords: List<GameUserRecord>,
        val oppoTeamRecords: List<GameUserRecord>,
    ) : TeamLeadersState

    data class Error(
        val errorMessage: String,
    ) : TeamLeadersState
}

data class TeamInfo(
    val teamId: Int,
    val teamPoint: Int,
    val topUser: UserInfo,
)

data class UserInfo(
    val userId: Int,
    val userName: String,
    val userTier: Int,
)
