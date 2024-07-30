package com.chill.mallang.ui.feature.nickname

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface NickNameUiState {

    @Immutable
    data class Error(
        val errorCode: Int = 0,
        val errorMessage: String = ""
    ) : NickNameUiState

    @Immutable
    data class Success(
        val nickName: String = ""
    ) : NickNameUiState

    @Immutable
    data object Init : NickNameUiState
}