package com.chill.mallang.ui.feature.login

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface LoginUiState {
    @Immutable
    data object Loading : LoginUiState

    @Immutable
    data object AuthLogin : LoginUiState

    @Immutable
    data class Success(
        val userEmail: String? = "",
        val userProfileImageUrl: String? = "",
    ) : LoginUiState

    @Immutable
    data class Error(
        val errorMessage: String = "",
    ) : LoginUiState
}
