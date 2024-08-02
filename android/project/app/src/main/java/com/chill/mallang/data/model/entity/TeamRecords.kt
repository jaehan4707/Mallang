package com.chill.mallang.data.model.entity

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class TeamRecords(
    val userRecord: UserRecord?,
    val myTeamRecords: List<UserRecord>,
    val oppoTeamRecords: List<UserRecord>,
)

@Immutable
@Serializable
data class UserRecord(
    val userId: Int,
    val userName: String,
    val userPlace: Int,
    val userScore: Int,
    val userPlayTime: Int,
) {
    constructor(userPlace: Int, userScore: Int, userPlayTime: Int) : this(
        0,
        "",
        userPlace,
        userScore,
        userPlayTime,
    )
}
