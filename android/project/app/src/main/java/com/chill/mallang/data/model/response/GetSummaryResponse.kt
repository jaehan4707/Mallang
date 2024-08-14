package com.chill.mallang.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetSummaryResponse(
    @SerialName("area")
    val areaName: String? = null,
    @SerialName("team mal totalScore")
    val malScore: Double? = null,
    @SerialName("team rang totalScore")
    val langScore: Double? = null,
    @SerialName("top score user")
    val topUserNickName: String? = null,
    @SerialName("top score")
    val topScore: Double? = null,
    @SerialName("victory team")
    val victoryFactionId: Int? = null,
)
