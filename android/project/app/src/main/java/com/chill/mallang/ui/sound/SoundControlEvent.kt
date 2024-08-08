package com.chill.mallang.ui.sound

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable



@Stable
sealed interface SoundControlEvent {
    @Immutable
    data class BackgroundVolumeChanged(
        val volume: Float,
    ) : SoundControlEvent

    @Immutable
    data class SoundEffectsVolumeChanged(
        val volume: Float,
    ) : SoundControlEvent

    @Immutable
    data class AlarmChanged(
        val alarm: Boolean,
    ) : SoundControlEvent
}
