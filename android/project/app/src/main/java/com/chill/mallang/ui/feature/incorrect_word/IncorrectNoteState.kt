package com.chill.mallang.ui.feature.incorrect_word

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface IncorrectNoteState {
    @Immutable
    data object Loading : IncorrectNoteState

    @Immutable
    data class Success(
        val wordList: List<IncorrectWord> = emptyList(),
    ) : IncorrectNoteState

    @Immutable
    data class Error(
        val errorMessage: String = "",
    ) : IncorrectNoteState
}
