package com.chill.mallang.data.model.entity

import kotlinx.serialization.Serializable

@Serializable
data class TeamList(
    val teams: List<Team>
)

@Serializable
data class Team(
    val teamName: String,
    val area: Int,
    val teamId: Int,
)
