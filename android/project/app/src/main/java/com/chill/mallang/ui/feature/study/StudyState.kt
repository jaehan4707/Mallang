package com.chill.mallang.ui.feature.study

data class StudyState(
    val studyId: Int = -1,
    val quizTitle: String = "",
    val quizScript: String = "",
    val wordList: List<String> = emptyList(),
    val isResultScreen: Boolean = false,
)
