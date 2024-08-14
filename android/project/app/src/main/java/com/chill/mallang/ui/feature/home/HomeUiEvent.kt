package com.chill.mallang.ui.feature.home

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface HomeUiEvent {
    @Immutable
    data object ShowSettingDialog : HomeUiEvent

    @Immutable
    data object CloseSettingDialog : HomeUiEvent

    @Immutable
    data object ShowEditNickNameDialog : HomeUiEvent

    @Immutable
    data object CloseEditNickNameDialog : HomeUiEvent

    @Immutable
    data object Refresh : HomeUiEvent

    @Immutable
    data class SignOut(
        val message: String,
    ) : HomeUiEvent

    @Immutable
    data class Error(
        val errorCode: Int,
        val errorMessage: String,
    ) : HomeUiEvent

    @Immutable
    data class Logout(
        val message: String,
    ) : HomeUiEvent
}
