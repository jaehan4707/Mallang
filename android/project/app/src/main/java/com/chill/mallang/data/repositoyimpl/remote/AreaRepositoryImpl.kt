package com.chill.mallang.data.repositoyimpl.remote

import com.chill.mallang.data.api.AreaApi
import com.chill.mallang.data.model.apiHandler
import com.chill.mallang.data.model.entity.Area
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.remote.AreaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AreaRepositoryImpl
    @Inject
    constructor(
        private val areaApi: AreaApi,
    ) : AreaRepository {
        override suspend fun getAreas(): Flow<ApiResponse<List<Area>>> =
            flow {
                val response =
                    apiHandler {
                        areaApi.getArea()
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
    }
