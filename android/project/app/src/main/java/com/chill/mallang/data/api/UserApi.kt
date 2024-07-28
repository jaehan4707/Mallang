package com.chill.mallang.data.api

import com.chill.mallang.data.model.request.JoinRequest
import com.chill.mallang.data.model.request.LoginRequest
import com.chill.mallang.data.model.response.JoinResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @POST("user/join")
    suspend fun join(
        @Body joinRequest: JoinRequest
    ): Response<JoinResponse>

    @DELETE
    suspend fun deleteUser(): Response<Void>

    @GET("login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<String>
}