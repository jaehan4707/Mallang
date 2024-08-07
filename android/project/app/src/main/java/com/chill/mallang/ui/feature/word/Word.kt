package com.chill.mallang.ui.feature.word

sealed class Word(
    open val word: String = "",
) {
    data class IncorrectWord(
        val studyId: Int,
        val finish: Boolean,
        override val word: String, // 문제 title
    ) : Word(word = word)

    data class WordCard(
        val meaning: String,
        val example: String,
        override val word: String,
    ) : Word(word = word)
}
