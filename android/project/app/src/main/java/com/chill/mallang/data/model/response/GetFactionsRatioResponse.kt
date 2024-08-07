package com.chill.mallang.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetFactionsRatioResponse(
    @SerialName("factionName")
    val name: String? = null,
    @SerialName("factionRatio")
    val ratio: Float? = null,
)
