package com.chill.mallang.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WordResponse(
    @SerialName("word")
    val word: String? = "",
    @SerialName("pos")
    val pos: String? = "",
    @SerialName("meaning")
    val meaning: String? = "",
    @SerialName("example")
    val example: String? = "",
)
