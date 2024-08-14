package com.chill.mallang.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GradingUserAnswerRequest(
    @SerialName("quizId")
    val quizId: Long,
    @SerialName("userId")
    val userId: Long,
    @SerialName("areaId")
    val areaId: Long,
    @SerialName("userAnswer")
    val userAnswer: String,
    @SerialName("answerTime")
    val answerTime: Int,
    @SerialName("created_at")
    val created_at: String,
)
