package com.chill.mallang.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudyResultResponse(
    @SerialName("quizTitle")
    val quizTitle: String? = null,
    @SerialName("quizScript")
    val quizScript: String? = null,
    @SerialName("wordList")
    val wordList: List<StudyWordResponse>,
    @SerialName("result")
    val result: Boolean? = null,
    @SerialName("systemAnswer")
    val systemAnswer: Int? = null,
)

@Serializable
data class StudyWordResponse(
    @SerialName("word")
    val word: String? = null,
    @SerialName("meaning")
    val meaning: String? = null,
)
