package com.chill.mallang.data.api

import com.chill.mallang.data.model.request.JoinRequest
import com.chill.mallang.data.model.request.LoginRequest
import com.chill.mallang.data.model.response.JoinResponse
import com.chill.mallang.data.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    @POST("user/join")
    suspend fun join(
        @Body joinRequest: JoinRequest
    ): Response<JoinResponse>

    @POST("user/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @GET("user/exists/nickname/{nickname}")
    suspend fun checkNickName(
        @Path("nickname") nickName: String
    ): Response<Unit>
}
