package com.chill.mallang.ui.feature.quiz

data class QuizState(
    val quizTitle: String = "",
    val quizScript: String = "",
    val wordList: List<String> = emptyList(),
    val isResultScreen: Boolean = false
)