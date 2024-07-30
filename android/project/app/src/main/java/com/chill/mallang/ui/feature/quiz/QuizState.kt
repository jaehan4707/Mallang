package com.chill.mallang.ui.feature.quiz

data class QuizState(
    val quizTitle: String = "",
    val quizScript: String = "",
    val wordList: List<String> = emptyList(),
    val selectedAnswer: Int = -1,
    val isResultScreen: Boolean = false
)