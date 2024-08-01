package com.chill.mallang.data.api

import com.chill.mallang.data.model.ResponseBody
import com.chill.mallang.data.model.entity.Area
import com.chill.mallang.data.model.entity.TeamList
import com.chill.mallang.data.model.entity.TryCount
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AreaApi {
    @GET("areas")
    suspend fun getArea(): Response<ResponseBody<List<Area>>>

    @GET("areas/status")
    suspend fun getOccupationStatus(): Response<ResponseBody<TeamList>>

    @GET("areas/try-count/{userId}")
    suspend fun getTryCount(
        @Path("userId") userId: Int,
    ): Response<ResponseBody<TryCount>>
}
