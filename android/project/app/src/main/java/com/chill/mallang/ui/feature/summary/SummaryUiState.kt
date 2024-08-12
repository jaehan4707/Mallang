package com.chill.mallang.ui.feature.summary

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.chill.mallang.data.model.entity.Summary
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Stable
sealed interface SummaryUiState {
    @Immutable
    data object Loading : SummaryUiState

    @Immutable
    data class Success(
        val summaryRecords: PersistentList<Summary> = persistentListOf(),
    ) : SummaryUiState

    @Immutable
    data class Error(
        val errorCode: Int = -1,
        val errorMessage: String = "",
    ) : SummaryUiState
}
