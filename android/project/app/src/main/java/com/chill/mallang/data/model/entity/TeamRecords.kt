package com.chill.mallang.data.model.entity

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class TeamRecords(
    val userRecord: MyRecord?,
    val myTeamRecords: List<UserRecord>,
    val oppoTeamRecords: List<UserRecord>,
)

@Immutable
@Serializable
data class MyRecord(
    val userPlace: Int,
    val userScore: Float?,
    val userPlayTime: Int?,
)

@Immutable
@Serializable
data class UserRecord(
    val userId: Long,
    val userName: String,
    val userPlace: Int,
    val userScore: Float?,
    val userPlayTime: Int?,
) {
    constructor(userPlace: Int, userScore: Float?, userPlayTime: Int) : this(
        0,
        "",
        userPlace,
        userScore,
        userPlayTime,
    )
}
