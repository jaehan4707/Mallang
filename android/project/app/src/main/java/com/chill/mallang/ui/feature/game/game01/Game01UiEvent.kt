package com.chill.mallang.ui.feature.game.game01

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface Game01UiEvent {
    @Immutable
    data object CompleteUserInfoLoad : Game01UiEvent

    @Immutable
    data object CompleteQuizIdsLoad : Game01UiEvent

    @Immutable
    data object CompleteQuizLoad : Game01UiEvent

    @Immutable
    data object CompletePostUserAnswer : Game01UiEvent

    @Immutable
    data object CompleteGameResultLoad : Game01UiEvent
}
