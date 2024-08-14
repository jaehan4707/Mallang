package com.chill.mallang.ui.feature.setting

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.chill.mallang.ui.sound.SoundManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val soundManager: SoundManager,
) : ViewModel() {
    val backgroundVolume = soundManager.backgroundVolume.map { it > 0f }
    val soundEffectsVolume = soundManager.soundEffectsVolume.map { it > 0f }
    val notificationAlarm = soundManager.notificationAlarm

    fun toggleBackgroundVolume(isEnabled: Boolean) {
        soundManager.setBackgroundVolume(if (isEnabled) 1f else 0f)
    }
    fun toggleSoundEffectsVolume(isEnabled: Boolean) {
        soundManager.setSoundEffectsVolume(if (isEnabled) 1f else 0f)
    }
    fun toggleNotificationAlarm(isEnabled: Boolean) {
        soundManager.setNotificationAlarm(isEnabled)
    }
}