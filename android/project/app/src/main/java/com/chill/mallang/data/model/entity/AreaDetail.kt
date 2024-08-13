package com.chill.mallang.data.model.entity

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class AreaDetail(
    val areaName: String,
    val myTeamInfo: TeamInfo,
    val oppoTeamInfo: TeamInfo,
)

@Serializable
@Immutable
data class TeamInfo(
    val teamId: Int,
    val teamPoint: Float?,
    val topUser: UserInfo?,
)

@Serializable
@Immutable
data class UserInfo(
    val userId: Int,
    val userName: String,
)
