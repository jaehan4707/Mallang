package com.chill.mallang.ui.feature.study_result

data class StudyResultState(
    val quizTitle: String = "",
    val quizScript: String = "",
    val wordList: List<Pair<String, String>> = emptyList(),
    val userAnswer: Int = -1,
    val systemAnswer: Int = -1
)