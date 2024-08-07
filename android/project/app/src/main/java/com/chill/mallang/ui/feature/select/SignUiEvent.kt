package com.chill.mallang.ui.feature.select

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface SignUiEvent {
    @Immutable
    data object SignUpSuccess : SignUiEvent

    @Immutable
    data class Error(
        val message: String,
    ) : SignUiEvent
}
