package com.chill.mallang.data.model.entity

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class Game01QuizData(
    val id: Long,
    val question: String,
    val answer: String,
    val difficulty: Int,
)
