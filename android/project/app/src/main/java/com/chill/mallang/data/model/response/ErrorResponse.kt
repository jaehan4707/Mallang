package com.chill.mallang.data.model.response

data class ErrorResponse(
    val errorCode: Int? = 0,
    val errorMessage: String? = "",
)
