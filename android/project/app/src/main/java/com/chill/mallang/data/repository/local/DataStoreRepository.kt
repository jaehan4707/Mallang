package com.chill.mallang.data.repository.local

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveAccessToken(token: String)
    suspend fun deleteAccessToken(token: String)
    suspend fun getAccessToken(): Flow<String?>
}