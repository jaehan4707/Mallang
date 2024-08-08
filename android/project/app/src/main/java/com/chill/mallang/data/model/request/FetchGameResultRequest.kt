package com.chill.mallang.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FetchGameResultRequest(
    @SerialName("areaID")
    val areaId: Long,
    @SerialName("userID")
    val userId: Long,
    @SerialName("factionID")
    val factionId: Long,
    @SerialName("quizID")
    val quizIds: List<Long>,
)
