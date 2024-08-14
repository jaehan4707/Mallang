package com.chill.mallang.ui.feature.home

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.chill.mallang.ui.component.experiencebar.ExperienceState

@Stable
sealed interface HomeUiState {
    @Immutable
    data object Loading : HomeUiState

    @Immutable
    data class LoadUserInfo(
        val nickName: String = "",
        val factionId: Long = 0,
        val experienceState: ExperienceState = ExperienceState.Loading(),
    ) : HomeUiState
}
