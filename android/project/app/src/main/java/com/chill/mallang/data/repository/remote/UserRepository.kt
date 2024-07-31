package com.chill.mallang.data.repository.remote

import com.chill.mallang.data.model.request.JoinRequest
import com.chill.mallang.data.model.response.ApiResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun join(request: JoinRequest): Flow<ApiResponse<Boolean>>

    suspend fun login(
        idToken: String,
        email: String,
    ): Flow<ApiResponse<Boolean>>

    suspend fun checkNickName(nickName: String): Flow<ApiResponse<Unit>>
}
