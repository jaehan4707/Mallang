package com.chill.mallang.data.repository.local

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveAccessToken(token: String)
    suspend fun deleteAccessToken()
    suspend fun getAccessToken(): Flow<String?>
    suspend fun saveUserEmail(email: String)
    suspend fun deleteUserEmail()
    suspend fun getUserEmail(): Flow<String?>
}
