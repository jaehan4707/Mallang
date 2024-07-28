package com.chill.mallang.ui.feature.select

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface SignUpUiState {
    @Immutable
    data class Loading(
        val idToken: String? = "",
        val userEmail: String? = "",
        val userNickName: String? = "",
        val userProfileImageUrl: String? = ""
    ) : SignUpUiState

    @Immutable
    data object Success : SignUpUiState

    @Immutable
    data class Error(
        val errorMessage: String = "",
    ) : SignUpUiState
}