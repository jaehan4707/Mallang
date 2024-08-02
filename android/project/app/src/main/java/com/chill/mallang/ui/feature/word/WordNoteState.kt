package com.chill.mallang.ui.feature.word

data class WordNoteState(
    val wordList: List<WordCard> = emptyList(),
    val incorrectList: List<IncorrectWord> = emptyList()
)
