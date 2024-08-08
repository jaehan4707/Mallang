package com.chill.mallang.ui.feature.word

sealed class Word(
    open val word: String = "",
) {
    data class IncorrectWord(
        val studyId: Int,
        override val word: String, // 문제 title
    ) : Word(word = word)

    data class CorrectWord(
        override val word: String,
        val pos: String,
        val meaning: String,
        val example: String,
    ) : Word(word = word)
}
