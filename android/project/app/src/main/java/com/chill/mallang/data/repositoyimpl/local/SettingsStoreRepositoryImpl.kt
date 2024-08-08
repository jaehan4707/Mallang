package com.chill.mallang.data.repositoyimpl.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import com.chill.mallang.data.repository.local.SettingsStoreRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsStoreRepositoryImpl
    @Inject
    constructor(
        private val dataStore: DataStore<Preferences>,
    ) : SettingsStoreRepository {
        override suspend fun setBackgroundVolume(volume: Float) {
            dataStore.edit { prefs ->
                prefs[BACKGROUND_VOLUME_KEY] = volume
            }
        }

        override suspend fun getBackgroundVolume(): Float {
            val stored = dataStore.data.map { prefs -> prefs[BACKGROUND_VOLUME_KEY] }.first()

            return stored ?: 1f.also { setBackgroundVolume(1f) }
        }

        override suspend fun setSoundEffectsVolume(volume: Float) {
            dataStore.edit { prefs ->
                prefs[SOUND_EFFECTS_VOLUME_KEY] = volume
            }
        }

        override suspend fun getSoundEffectsVolume(): Float {
            val stored = dataStore.data.map { prefs -> prefs[SOUND_EFFECTS_VOLUME_KEY] }.first()

            return stored ?: 1f.also { setSoundEffectsVolume(1f) }
        }

        override suspend fun setNotificationAlarm(alarm: Boolean) {
            dataStore.edit { prefs ->
                prefs[NOTIFICATION_ALARM_KEY] = alarm
            }
        }

        override suspend fun getNotificationAlarm(): Boolean {
            val stored = dataStore.data.map { prefs -> prefs[NOTIFICATION_ALARM_KEY] }.first()

            return stored ?: false.also { setNotificationAlarm(false) }
        }

        companion object {
            val BACKGROUND_VOLUME_KEY = floatPreferencesKey("BACKGROUND_VOLUME_KEY")
            val SOUND_EFFECTS_VOLUME_KEY = floatPreferencesKey("SOUND_EFFECTS_VOLUME_KEY")
            val NOTIFICATION_ALARM_KEY = booleanPreferencesKey("NOTIFICATION_ALARM_KEY")
        }
    }
