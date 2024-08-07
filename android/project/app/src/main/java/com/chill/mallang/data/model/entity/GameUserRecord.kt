package com.chill.mallang.data.model.entity

import kotlinx.serialization.Serializable

@Serializable
data class GameUserRecord(
    val userPlace: Int,
    val userName: String,
    val userScore: Float,
)
