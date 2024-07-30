package com.chill.mallang.ui.feature.quiz

data class QuizResultState(
    val quizTitle: String = "",
    val quizScript: String = "",
    val wordList: List<Pair<String, String>> = emptyList(),
    val expandedAnswer: Int = -1,
    val userAnswer: Int = -1,
    val systemAnswer: Int = -1
)