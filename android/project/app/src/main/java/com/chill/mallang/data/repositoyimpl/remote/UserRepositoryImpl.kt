package com.chill.mallang.data.repositoyimpl.remote

import com.chill.mallang.data.api.UserApi
import com.chill.mallang.data.model.apiHandler
import com.chill.mallang.data.model.request.JoinRequest
import com.chill.mallang.data.model.request.LoginRequest
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.local.DataStoreRepository
import com.chill.mallang.data.repository.remote.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val dataStoreRepository: DataStoreRepository,
) : UserRepository {

    override suspend fun join(request: JoinRequest): Flow<ApiResponse<String>> = flow {
        val response = apiHandler {
            userApi.join(request)
        }
        when (response) {
            is ApiResponse.Success -> {
                val accessToken = response.data.data?.token ?: ""
                dataStoreRepository.saveAccessToken(accessToken)
                dataStoreRepository.saveUserEmail(request.userEmail)
                emit(ApiResponse.Success(accessToken))
            }

            is ApiResponse.Error -> {
                emit(
                    ApiResponse.Error(
                        errorCode = response.errorCode,
                        errorMessage = response.errorMessage
                    )
                )
            }

            ApiResponse.Init -> {
                emit(ApiResponse.Init)
            }
        }
    }

    override suspend fun logout() {}

    override suspend fun deleteUser() {}
    override suspend fun login(idToken: String, email: String): Flow<ApiResponse<String>> = flow {
        val response = apiHandler {
            userApi.login(
                LoginRequest(
                    idToken = idToken,
                    email = email,
                )
            )
        }
        when (response) {
            is ApiResponse.Success -> {
                val accessToken = response.data ?: ""
                dataStoreRepository.saveAccessToken(accessToken)
                dataStoreRepository.saveUserEmail(email)
                emit(ApiResponse.Success(data = accessToken))
            }

            is ApiResponse.Error -> {
                emit(
                    ApiResponse.Error(
                        errorCode = response.errorCode,
                        errorMessage = response.errorMessage
                    )
                )
            }

            ApiResponse.Init -> {
                emit(ApiResponse.Init)
            }
        }
    }

}