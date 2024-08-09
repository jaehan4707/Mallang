package com.chill.mallang.ui.feature.summary

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface SummaryUiState {
    @Immutable
    data object Loading : SummaryUiState

    @Immutable
    data object Success : SummaryUiState
}
