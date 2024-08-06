package com.chill.mallang.data.model.entity

import kotlinx.serialization.Serializable

@Serializable
data class GameUserRecord(
    val rank: Int,
    val nickname: String,
    val score: Float,
)
