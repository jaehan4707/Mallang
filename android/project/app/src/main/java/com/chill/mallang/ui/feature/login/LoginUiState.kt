package com.chill.mallang.ui.feature.login

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@Stable
data class LoginUiState(
    val userName: String? = "",
    val userEmail: String? = "",
    val userProfileImageUrl: String? = "",
)