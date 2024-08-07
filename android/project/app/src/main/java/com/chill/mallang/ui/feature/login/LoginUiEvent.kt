package com.chill.mallang.ui.feature.login

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface LoginUiEvent {
    @Immutable
    data object AuthLogin : LoginUiEvent
}
