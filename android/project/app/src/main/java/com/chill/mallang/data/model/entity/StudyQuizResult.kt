package com.chill.mallang.data.model.entity

data class StudyQuizResult(
    val quizTitle: String,
    val quizScript: String,
    val wordList: List<StudyResultWord>,
    val result: Boolean,
    val systemAnswer: Int,
)
