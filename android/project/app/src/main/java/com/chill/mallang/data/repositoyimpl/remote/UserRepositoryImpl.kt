package com.chill.mallang.data.repositoyimpl.remote

import com.chill.mallang.data.api.UserApi
import com.chill.mallang.data.model.apiHandler
import com.chill.mallang.data.model.request.JoinRequest
import com.chill.mallang.data.model.request.LoginRequest
import com.chill.mallang.data.model.request.UpdateNickNameRequest
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.model.response.UserInfo
import com.chill.mallang.data.repository.local.DataStoreRepository
import com.chill.mallang.data.repository.remote.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl
@Inject
constructor(
    private val userApi: UserApi,
    private val dataStoreRepository: DataStoreRepository,
) : UserRepository {
    override suspend fun join(request: JoinRequest): Flow<ApiResponse<Boolean>> =
        flow {
            val response =
                apiHandler {
                    userApi.join(request)
                }
            when (response) {
                is ApiResponse.Success -> {
                    response.data?.data?.let {
                        emit(ApiResponse.Success(it.isRegister ?: true))
                    }
                }

                is ApiResponse.Error -> {
                    emit(
                        ApiResponse.Error(
                            errorCode = response.errorCode,
                            errorMessage = response.errorMessage,
                        ),
                    )
                }

                ApiResponse.Init -> {
                    emit(ApiResponse.Init)
                }
            }
        }

    override suspend fun login(
        idToken: String,
        email: String,
    ): Flow<ApiResponse<Boolean>> =
        flow {
            val response =
                apiHandler {
                    userApi.login(
                        LoginRequest(
                            idToken = idToken,
                            email = email,
                        ),
                    )
                }
            when (response) {
                is ApiResponse.Success -> {
                    response.data?.data?.let {
                        dataStoreRepository.saveAccessToken(it.token ?: "")
                        dataStoreRepository.saveUserEmail(email)
                        emit(ApiResponse.Success(it.isRegister ?: true))
                    }
                }

                is ApiResponse.Error -> {
                    emit(
                        ApiResponse.Error(
                            errorCode = response.errorCode,
                            errorMessage = response.errorMessage,
                        ),
                    )
                }

                ApiResponse.Init -> {
                    emit(ApiResponse.Init)
                }
            }
        }

    override suspend fun checkNickName(nickName: String): Flow<ApiResponse<Unit>> =
        flow {
            val response =
                apiHandler {
                    userApi.checkNickName(nickName)
                }
            when (response) {
                is ApiResponse.Success -> {
                    emit(ApiResponse.Success(null))
                }

                is ApiResponse.Error -> {
                    emit(
                        ApiResponse.Error(
                            errorCode = response.errorCode,
                            errorMessage = response.errorMessage,
                        ),
                    )
                }

                ApiResponse.Init -> {}
            }
        }

    override suspend fun getUserInfo(): Flow<ApiResponse<UserInfo>> =
        flow {
            val response =
                apiHandler {
                    userApi.getUserInfo()
                }
            when (response) {
                is ApiResponse.Success -> {
                    emit(ApiResponse.Success(response.data?.data))
                }

                is ApiResponse.Error -> {
                    emit(
                        ApiResponse.Error(
                            errorCode = response.errorCode,
                            errorMessage = response.errorMessage,
                        ),
                    )
                }

                ApiResponse.Init -> {}
            }
        }

    override suspend fun checkUserEmail(userEmail: String): Flow<ApiResponse<Unit>> =
        flow {
            val response =
                apiHandler {
                    userApi.checkUserEmail(userEmail)
                }
            when (response) {
                is ApiResponse.Success -> {
                    emit(ApiResponse.Success(Unit))
                }

                is ApiResponse.Error -> {
                    emit(
                        ApiResponse.Error(
                            errorCode = response.errorCode,
                            errorMessage = response.errorMessage,
                        ),
                    )
                }

                ApiResponse.Init -> {}
            }
        }

    override suspend fun updateNickName(userNickName: String): Flow<ApiResponse<String>> =
        flow {
            val response =
                apiHandler {
                    userApi.updateNickName(UpdateNickNameRequest(userNickName))
                }
            when (response) {
                is ApiResponse.Success -> {
                    emit(ApiResponse.Success(userNickName))
                }

                is ApiResponse.Error -> {
                    emit(
                        ApiResponse.Error(
                            errorCode = response.errorCode,
                            errorMessage = response.errorMessage,
                        ),
                    )
                }

                ApiResponse.Init -> {}
            }
        }

    override suspend fun signOut(): Flow<ApiResponse<String>> = flow {
        val response = apiHandler {
            userApi.signOut()
        }
        when (response) {
            is ApiResponse.Error -> emit(
                ApiResponse.Error(
                    errorCode = response.errorCode,
                    errorMessage = response.errorMessage
                )
            )

            ApiResponse.Init -> {}
            is ApiResponse.Success -> emit(ApiResponse.Success(response.data?.success))
        }
    }
}
