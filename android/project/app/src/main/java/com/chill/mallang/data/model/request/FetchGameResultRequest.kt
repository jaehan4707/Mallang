package com.chill.mallang.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FetchGameResultRequest(
    @SerialName("areaID")
    val areaId: Int,
    @SerialName("userID")
    val userId: Int,
    @SerialName("factionID")
    val factionId: Int,
    @SerialName("quizID")
    val quizIds: List<Int>,
)
