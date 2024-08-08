package com.chill.mallang.data.model.entity

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class GameUserRecord(
    @SerialName("name")
    val userName: String,
    @SerialName("score")
    val userScore: Float,
)
