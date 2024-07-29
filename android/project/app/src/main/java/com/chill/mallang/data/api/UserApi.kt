package com.chill.mallang.data.api

import com.chill.mallang.data.model.request.JoinRequest
import com.chill.mallang.data.model.request.LoginRequest
import com.chill.mallang.data.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @POST("join")
    suspend fun join(
        @Body joinRequest: JoinRequest
    ): Response<LoginResponse>

    @DELETE
    suspend fun deleteUser(): Response<Void>

    @GET("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<String>
}