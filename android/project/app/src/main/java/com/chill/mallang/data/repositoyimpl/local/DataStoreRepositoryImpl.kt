package com.chill.mallang.data.repositoyimpl.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.chill.mallang.data.model.entity.User
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.local.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class DataStoreRepositoryImpl
    @Inject
    constructor(
        private val dataStore: DataStore<Preferences>,
    ) : DataStoreRepository {
        override suspend fun saveAccessToken(token: String) {
            dataStore.edit { prefs ->
                prefs[ACCESS_TOKEN_KEY] = token
            }
        }

        override suspend fun deleteAccessToken() {
            dataStore.edit { prefs ->
                prefs.remove(ACCESS_TOKEN_KEY)
            }
        }

        override suspend fun getAccessToken() =
            flow {
                emit(
                    dataStore.data
                        .map { prefs ->
                            prefs[ACCESS_TOKEN_KEY]
                        }.first(),
                )
            }

        override suspend fun saveUserEmail(email: String) {
            dataStore.edit { prefs ->
                prefs[USER_EMAIL_KEY] = email
            }
        }

        override suspend fun deleteUserEmail() {
            dataStore.edit { prefs ->
                prefs.remove(USER_EMAIL_KEY)
            }
        }

        override suspend fun getUserEmail() =
            dataStore.data
                .map { prefs ->
                    prefs[USER_EMAIL_KEY]
                }.first()

        override suspend fun logout(): Flow<ApiResponse<Unit>> =
            flow {
                val emailDeletionResult = runCatching { deleteUserEmail() }
                val tokenDeletionResult = runCatching { deleteAccessToken() }
                if (emailDeletionResult.isSuccess && tokenDeletionResult.isSuccess) {
                    emit(ApiResponse.Success(Unit))
                } else {
                    val errors = mutableListOf<String>()
                    emailDeletionResult.exceptionOrNull()?.message?.let { errors.add(it) }
                    tokenDeletionResult.exceptionOrNull()?.message?.let { errors.add(it) }
                    if (errors.isNotEmpty()) {
                        emit(
                            ApiResponse.Error(
                                errorCode = 500,
                                errorMessage = errors.joinToString(", "),
                            ),
                        )
                    }
                }
            }

        override suspend fun saveUser(user: User) {
            dataStore.edit { prefs ->
                prefs[USER_ID_KEY] = user.id.toString()
                prefs[USER_FACTION_ID] = user.factionId.toString()
                prefs[USER_LEVEL_KEY] = user.level.toString()
                prefs[USER_EXP_KEY] = user.exp.toString()
            }
        }

        override suspend fun getUserId() =
            dataStore.data
                .map { prefs ->
                    prefs[USER_ID_KEY].toString().toLongOrNull()
                }.first()

        override suspend fun getFactionId() =
            dataStore.data
                .map { prefs ->
                    prefs[USER_FACTION_ID].toString().toLongOrNull()
                }.first()


        override suspend fun isUserFirstLaunched(): Flow<Boolean> =
            flow {
                val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                val isFirstLaunch =
                    dataStore.data
                        .map { prefs -> prefs[USER_LASTED_VISITED_DAY_KEY] }
                        .first() != todayDate
                if (isFirstLaunch) {
                    dataStore.edit { prefs ->
                        prefs[USER_LASTED_VISITED_DAY_KEY] = todayDate
                    }
                }
                emit(isFirstLaunch)
            }

        override suspend fun saveLevel(level: Int) {
            dataStore.edit { prefs ->
                prefs[USER_LEVEL_KEY] = level.toString()
            }
        }

        override suspend fun saveExp(exp: Float) {
            dataStore.edit { prefs ->
                prefs[USER_EXP_KEY] = exp.toString()
            }
        }

        override suspend fun getLevel(): Int? =
            dataStore.data
                .map { prefs ->
                    prefs[USER_LEVEL_KEY].toString().toIntOrNull()
                }.first()

        override suspend fun getExp(): Float? =
            dataStore.data
                .map { prefs ->
                    prefs[USER_EXP_KEY].toString().toFloatOrNull()
                }.first()


        companion object {
            val ACCESS_TOKEN_KEY = stringPreferencesKey("ACCESS_TOKEN_KEY")
            val USER_EMAIL_KEY = stringPreferencesKey("USER_EMAIL_KEY")
            val USER_FACTION_ID = stringPreferencesKey("USER_FACTION_ID_KEY")
            val USER_ID_KEY = stringPreferencesKey("USER_ID_KEY")

            val USER_LASTED_VISITED_DAY_KEY = stringPreferencesKey("USER_LASTED_VISITED_DAY_KEY")

            val USER_LEVEL_KEY = stringPreferencesKey("USER_LEVEL_KEY")
            val USER_EXP_KEY = stringPreferencesKey("USER_EXP_KEY")

        }
    }
