package com.chill.mallang.ui.feature.word

data class IncorrectWord(
    val word: String = "",
    val quizTitle: String = "",
    val quizScript: String = "",
    val wordList: List<Pair<String, String>> = emptyList(),
    val userAnswer: Int = -1,
    val systemAnswer: Int = -1,
    val finish: Boolean = false,
)
