package com.chill.mallang.ui.feature.study_result

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.chill.mallang.data.model.entity.StudyResultWord

@Stable
sealed interface StudyResultState {
    @Immutable
    data object Loading : StudyResultState

    @Immutable
    data class Success(
        val quizTitle: String = "",
        val quizScript: String = "",
        val wordList: List<StudyResultWord> = emptyList(),
        val result: Boolean = false,
        val systemAnswer: Int = -1,
    ) : StudyResultState

    @Immutable
    data class Error(
        val errorMessage: String = "",
    ) : StudyResultState
}
