package com.chill.mallang.data.repository.remote

import com.chill.mallang.data.model.entity.Area
import com.chill.mallang.data.model.response.ApiResponse
import kotlinx.coroutines.flow.Flow

interface AreaRepository {
    suspend fun getAreas(): Flow<ApiResponse<List<Area>>>
}
