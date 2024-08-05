package com.chill.mallang.data.repository.local

interface SettingsStoreRepository {
    suspend fun setBackgroundVolume(volume: Float)

    suspend fun getBackgroundVolume(): Float

    suspend fun setSoundEffectsVolume(volume: Float)

    suspend fun getSoundEffectsVolume(): Float

    suspend fun setNotificationAlarm(alarm: Boolean)

    suspend fun getNotificationAlarm(): Boolean
}
