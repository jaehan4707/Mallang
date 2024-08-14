package com.chill.mallang.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudyResponse(
    @SerialName("studyId")
    val studyId: Long? = null,
    @SerialName("quizTitle")
    val quizTitle: String? = null,
    @SerialName("quizScript")
    val quizScript: String? = null,
    @SerialName("wordList")
    val wordList: List<String>? = null
)