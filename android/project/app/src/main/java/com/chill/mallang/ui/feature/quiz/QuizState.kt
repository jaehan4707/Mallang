package com.chill.mallang.ui.feature.quiz

data class QuizState(
    val quizTitle: String = "",
    val quizScript: String = "",
    val quizWord: String = "",
    val wordList: List<Pair<String, String>> = emptyList(),
    var selectedAnswer: Int = -1,
    var expandedAnswer: Int = -1,
    val isResultScreen: Boolean = false,
    val systemAnswer: Int = -1,
    val isAnswer: Boolean = false
)
