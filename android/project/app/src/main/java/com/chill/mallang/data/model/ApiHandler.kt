package com.chill.mallang.data.model

import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.model.response.ErrorResponse
import com.google.gson.Gson
import retrofit2.Response

suspend fun <T> apiHandler(
    apiResponse: suspend () -> Response<T>,
): ApiResponse<T> {
    runCatching {
        val response = apiResponse.invoke()
        val errorData = Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
        if (response.isSuccessful) {
            response.body()?.let { body ->
                return ApiResponse.Success(body)
            }
        } else {
            return ApiResponse.Error(
                errorCode = errorData.errorCode ?: 0,
                errorMessage = errorData.errorMessage ?: "",
            )
        }
    }.onFailure {
        return ApiResponse.Error(errorMessage = it.message ?: "")
    }
    return ApiResponse.Init
}
