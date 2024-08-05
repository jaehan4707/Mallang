package com.chill.mallang.ui.sound

import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool
import com.chill.mallang.core.di.ApplicationScope
import com.chill.mallang.data.repository.local.SettingsStoreRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundManager @Inject constructor(
    @ApplicationContext private val context: Context,
    @ApplicationScope private val applicationScope: CoroutineScope,
    private val settingsStoreRepository: SettingsStoreRepository,
) {
    private var mediaPlayer: MediaPlayer = MediaPlayer.create(context, 1)
    private var soundPool: SoundPool = SoundPool.Builder().build()

    private val _backgroundVolume: MutableStateFlow<Float> = MutableStateFlow(1f)
    val backgroundVolume: StateFlow<Float> = _backgroundVolume.asStateFlow()

    private val _soundEffectsVolume: MutableStateFlow<Float> = MutableStateFlow(1f)
    val soundEffectsVolume: StateFlow<Float> = _soundEffectsVolume.asStateFlow()

    private val _notificationAlarm: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val notificationAlarm: StateFlow<Boolean> = _notificationAlarm.asStateFlow()

    init {
        applicationScope.launch {
            _backgroundVolume.value = settingsStoreRepository.getBackgroundVolume()
            _soundEffectsVolume.value = settingsStoreRepository.getSoundEffectsVolume()
            _notificationAlarm.value = settingsStoreRepository.getNotificationAlarm()
        }
    }
}
