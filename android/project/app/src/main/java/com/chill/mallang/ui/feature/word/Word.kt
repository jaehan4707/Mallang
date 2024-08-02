package com.chill.mallang.ui.feature.word

sealed class Word(
    open val word: String = "",
) {
    data class IncorrectWord(
        val quizId: Int,
        val finish: Boolean,
        override val word: String,
    ) : Word(word = word)

    data class WordCard(
        val meaning: String,
        val example: String,
        override val word: String,
    ) : Word(word = word)
}
