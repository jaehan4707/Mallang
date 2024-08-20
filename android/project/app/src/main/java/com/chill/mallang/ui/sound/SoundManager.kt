package com.chill.mallang.ui.sound

import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool
import com.chill.mallang.R
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

/**
 * ### 배경음/효과음 매니저
 *
 * 앱 전역에서 사용가능한 음악 플레이어.
 *
 * `backgroundMusic` 관련 함수로 긴 배경음악을 재생.
 *
 * `soundEffect` 관련 함수로 효과음을 재생.
 *
 * ViewModel로의 주입법
 * ```kotlin
 * @Inject
 * constructor(
 *     private val savedStateHandle: SavedStateHandle,
 *     val soundManager: SoundManager,
 * ) : ViewModel()
 * ```
 * Composable에서의 사용법
 * ```kotlin
 * DisposableEffect(Unit) {
 *     viewModel.soundManager.playBackgroundMusic(R.raw.background)
 *     onDispose {
 *         viewModel.soundManager.stopBackgroundMusic()
 *     }
 * }
 * ```
 */
@Singleton
class SoundManager
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
        @ApplicationScope private val applicationScope: CoroutineScope,
        private val settingsStoreRepository: SettingsStoreRepository,
    ) {
        private var currentBackgroundId: Int? = null

        private var mediaPlayer: MediaPlayer? = null
        private var soundPool: SoundPool = SoundPool.Builder().build()
        private val soundMap = mutableMapOf<Int, Int>()

        private val _backgroundVolume: MutableStateFlow<Float> = MutableStateFlow(1f)
        val backgroundVolume: StateFlow<Float> = _backgroundVolume.asStateFlow()

        private val _soundEffectsVolume: MutableStateFlow<Float> = MutableStateFlow(1f)
        val soundEffectsVolume: StateFlow<Float> = _soundEffectsVolume.asStateFlow()

        private val _notificationAlarm: MutableStateFlow<Boolean> = MutableStateFlow(false)
        val notificationAlarm: StateFlow<Boolean> = _notificationAlarm.asStateFlow()

        init {
            loadSounds()
            applicationScope.launch {
                _backgroundVolume.value = settingsStoreRepository.getBackgroundVolume()
                _soundEffectsVolume.value = settingsStoreRepository.getSoundEffectsVolume()
                _notificationAlarm.value = settingsStoreRepository.getNotificationAlarm()
            }
        }

        // 사용하는 bgm 미리 로드
        private fun loadSounds() {
            soundMap[R.raw.effect_click] = soundPool.load(context, R.raw.effect_click, 1)
            soundMap[R.raw.effect_fail] = soundPool.load(context, R.raw.effect_fail, 1)
            soundMap[R.raw.effect_success] = soundPool.load(context, R.raw.effect_success, 1)
            soundMap[R.raw.effect_round_load] = soundPool.load(context, R.raw.effect_round_load, 1)
            soundMap[R.raw.effect_point_indicator] = soundPool.load(context, R.raw.effect_point_indicator, 1)
            soundMap[R.raw.effect_stamp] = soundPool.load(context, R.raw.effect_stamp, 1)
            soundMap[R.raw.effect_total_point] = soundPool.load(context, R.raw.effect_total_point, 1)
            soundMap[R.raw.effect_game_win] = soundPool.load(context, R.raw.effect_game_win, 1)
            soundMap[R.raw.effect_game_splash] = soundPool.load(context, R.raw.effect_game_splash, 1)
            soundMap[R.raw.effect_seal_down] = soundPool.load(context, R.raw.effect_seal_down, 1)
            soundMap[R.raw.effect_seal_up] = soundPool.load(context, R.raw.effect_seal_up, 1)
            soundMap[R.raw.effect_game_curtain_call] = soundPool.load(context, R.raw.effect_game_curtain_call, 1)
        }

        fun playBackgroundMusic(resId: Int) {
            if (currentBackgroundId == resId && mediaPlayer?.isPlaying == true) return

            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(context, resId)
            } else {
                mediaPlayer?.run {
                    if (isPlaying) stop()
                    reset()
                    try {
                        val afd = context.resources.openRawResourceFd(resId)
                        setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                        prepare()
                    } catch (e: Exception) {
                        return
                    }
                }
            }
            mediaPlayer?.run {
                isLooping = true
                setVolume(_backgroundVolume.value, _backgroundVolume.value)
                start()
                currentBackgroundId = resId
            }
        }

        fun stopBackgroundMusic(resId: Int) {
            if (currentBackgroundId == resId) {
                mediaPlayer?.run {
                    stop()
                    prepare()
                }
            }
        }

        fun pauseBackgroundMusic() {
            mediaPlayer?.pause()
        }

        fun resumeBackgroundMusic() {
            mediaPlayer?.run {
                if (!isPlaying) mediaPlayer?.start()
            }
        }

        fun playSoundEffect(soundResId: Int) {
            soundMap[soundResId]?.let { id ->
                soundPool.play(id, _soundEffectsVolume.value, _soundEffectsVolume.value, 1, 0, 1f)
            }
        }

        fun setBackgroundVolume(volume: Float) {
            applicationScope.launch {
                settingsStoreRepository.setBackgroundVolume(volume)
                _backgroundVolume.value = volume
                mediaPlayer?.setVolume(volume, volume)
            }
        }

        fun setSoundEffectsVolume(volume: Float) {
            _soundEffectsVolume.value = volume
        }

        fun setNotificationAlarm(alarm: Boolean) {
            _notificationAlarm.value = alarm
        }
    }
