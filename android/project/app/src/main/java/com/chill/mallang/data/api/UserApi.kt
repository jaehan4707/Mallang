package com.chill.mallang.data.api

import com.chill.mallang.data.model.ResponseBody
import com.chill.mallang.data.model.request.JoinRequest
import com.chill.mallang.data.model.request.LoginRequest
import com.chill.mallang.data.model.request.UpdateNickNameRequest
import com.chill.mallang.data.model.response.GetUserInfoResponse
import com.chill.mallang.data.model.response.JoinResponse
import com.chill.mallang.data.model.response.LoginResponse
import com.chill.mallang.data.model.response.UpdateNickNameResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    @POST("user/join")
    suspend fun join(
        @Body joinRequest: JoinRequest,
    ): Response<JoinResponse>

    @POST("user/login")
    suspend fun login(
        @Body loginRequest: LoginRequest,
    ): Response<LoginResponse>

    @GET("user/exists/nickname/{nickname}")
    suspend fun checkNickName(
        @Path("nickname") nickName: String,
    ): Response<Unit>

    @GET("user/info")
    suspend fun getUserInfo(): Response<GetUserInfoResponse>

    @GET("user/exists/email/{email}")
    suspend fun checkUserEmail(
        @Path("email") userEmail: String,
    ): Response<ResponseBody<Unit>>

    @PATCH("user/nickname")
    suspend fun updateNickName(
        @Body updateNickNameRequest: UpdateNickNameRequest,
    ): Response<UpdateNickNameResponse>
}
