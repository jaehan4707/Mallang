package com.chill.mallang.data.api

import com.chill.mallang.data.model.ResponseBody
import com.chill.mallang.data.model.response.GetFactionsRatioResponse
import retrofit2.Response
import retrofit2.http.GET

interface FactionApi {
    @GET("factions/ratio")
    suspend fun getFactionsRatio(): Response<ResponseBody<List<GetFactionsRatioResponse>>>
}
