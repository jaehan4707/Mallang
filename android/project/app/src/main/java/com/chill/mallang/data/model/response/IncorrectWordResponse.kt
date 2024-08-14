package com.chill.mallang.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IncorrectWordResponse(
    @SerialName("studyId")
    val studyId: Long? = null,
    @SerialName("word")
    val script: String? = ""
)
