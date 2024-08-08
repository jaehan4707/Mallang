package com.chill.mallang.ui.feature.word

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface WordNoteState {
    @Immutable
    data object Loading : WordNoteState

    @Immutable
    data class Success(
        val wordList: List<Word> = emptyList(),
    ) : WordNoteState

    @Immutable
    data class Error(
        val errorMessage: String = "",
    ) : WordNoteState
}
