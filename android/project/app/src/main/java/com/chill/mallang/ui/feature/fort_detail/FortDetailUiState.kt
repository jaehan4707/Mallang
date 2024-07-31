package com.chill.mallang.ui.feature.fort_detail

import androidx.compose.runtime.Stable

@Stable
data class FortDetailUiState(
    val occupationState: OccupationState =
        OccupationState.Success(
            areaName = "구미 캠퍼스",
            myTeamInfo = TeamInfo(1, 3990, UserInfo(1, "말대표", 1)),
            oppoTeamInfo = TeamInfo(2, 2990, UserInfo(2, "랑대표", 1)),
        ),
    val teamLeadersState: TeamLeadersState =
        TeamLeadersState.Success(
            userRecord = UserRecord(1, 1, "말대표", 100, 300),
            myTeamRecords =
                listOf(
                    UserRecord(1, 1, "말대표", 100, 300),
                    UserRecord(2, 3, "말부하", 97, 300),
                ),
            oppoTeamRecords =
                listOf(
                    UserRecord(1, 4, "랑대표", 100, 300),
                    UserRecord(2, 5, "랑부하", 97, 300),
                ),
        ),
)

@Stable
sealed interface OccupationState {
    data class Loading(
        val loadingMessage: String,
    ) : OccupationState

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
    data class Loading(
        val loadingMessage: String,
    ) : TeamLeadersState

    data class Success(
        val userRecord: UserRecord,
        val myTeamRecords: List<UserRecord>,
        val oppoTeamRecords: List<UserRecord>,
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

data class UserRecord(
    val userPlace: Int,
    val userId: Int,
    val userName: String,
    val userScore: Int,
    val userPlayTime: Int,
)
