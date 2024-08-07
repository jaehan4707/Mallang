package com.chill.mallang.data.repositoyimpl.remote

import com.chill.mallang.data.api.FactionApi
import com.chill.mallang.data.model.apiHandler
import com.chill.mallang.data.model.entity.Faction
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.remote.FactionRepository
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FactionRepositoryImpl
    @Inject
    constructor(
        private val factionApi: FactionApi,
    ) : FactionRepository {
        override suspend fun getFactionRatios(): Flow<ApiResponse<PersistentList<Faction>>> =
            flow {
                val response =
                    apiHandler {
                        factionApi.getFactionsRatio()
                    }
                when (response) {
                    is ApiResponse.Success ->
                        emit(
                            ApiResponse.Success(
                                body =
                                    response.body
                                        ?.data
                                        ?.map {
                                            Faction(
                                                ratio = it.ratio ?: 0f,
                                                name = it.name ?: "",
                                            )
                                        }?.toPersistentList(),
                            ),
                        )

                    is ApiResponse.Error -> {
                        emit(ApiResponse.Error(response.errorCode, response.errorMessage))
                    }

                    ApiResponse.Init -> {}
                }
            }
    }
