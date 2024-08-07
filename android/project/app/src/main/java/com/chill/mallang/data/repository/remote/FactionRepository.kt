package com.chill.mallang.data.repository.remote

import com.chill.mallang.data.model.entity.Faction
import com.chill.mallang.data.model.response.ApiResponse
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.flow.Flow

interface FactionRepository {
    suspend fun getFactionRatios(): Flow<ApiResponse<PersistentList<Faction>>>
}
