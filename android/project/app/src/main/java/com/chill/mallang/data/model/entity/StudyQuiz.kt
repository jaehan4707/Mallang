package com.chill.mallang.data.model.entity

data class StudyQuiz(
    val studyId: Long,
    val quizTitle: String,
    val quizScript: String,
    val wordList: List<String>,
)
