package com.chill.mallang.ui.feature.home

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface HomeUiState {
    @Immutable
    data object Loading : HomeUiState

    @Immutable
    data class LoadUserInfo(
        val userNickName: String = "",
        val userFaction: String = "",
        val showSettingDialog: Boolean = false,
        val showEditNickNameDialog: Boolean = false,
    ) : HomeUiState

    @Immutable
    data class Error(
        val errorCode: Int = 0,
        val errorMessage: String = "",
    ) : HomeUiState
}
