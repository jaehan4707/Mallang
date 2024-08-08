package com.chill.mallang.data.repository.local

import com.chill.mallang.data.model.entity.User
import com.chill.mallang.data.model.response.ApiResponse
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveAccessToken(token: String)

    suspend fun deleteAccessToken()

    suspend fun getAccessToken(): Flow<String?>

    suspend fun saveUserEmail(email: String)

    suspend fun deleteUserEmail()

    suspend fun getUserEmail(): String?

    suspend fun logout(): Flow<ApiResponse<Unit>>

    suspend fun saveUser(user: User)

    suspend fun getUserId(): Int?

    suspend fun getFactionId(): Int?
}
