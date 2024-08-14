package com.chill.mallang.ui.feature.study

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface StudyState {
    @Immutable
    data object Loading : StudyState

    @Immutable
    data class Success(
        val studyId: Long = -1,
        val quizTitle: String = "",
        val quizScript: String = "",
        val wordList: List<String> = emptyList(),
        val isResultScreen: Boolean = false,
    ) : StudyState

    @Immutable
    data class Error(
        val errorMessage: String = "",
    ) : StudyState

    @Immutable
    data class SubmitSuccess(
        val studyId: Long
    ) : StudyState
}
