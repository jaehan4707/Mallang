package com.chill.mallang.ui.component.experiencebar

import androidx.compose.runtime.Immutable

sealed interface ExperienceState {
    val level: Int

    @Immutable
    class Loading(
        override val level: Int = 0,
    ) : ExperienceState

    @Immutable
    data class Animate(
        val prevValue: Float,
        val currentValue: Float,
        override val level: Int,
    ) : ExperienceState

    @Immutable
    data class Static(
        val value: Float,
        override val level: Int,
    ) : ExperienceState
}
