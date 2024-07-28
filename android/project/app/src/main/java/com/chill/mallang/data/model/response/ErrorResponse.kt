package com.chill.mallang.data.model.response

data class ErrorResponse(
    val status: Int? = 0,
    val httpStatus: String? = "",
    val code: String? = "",
    val message: String? = "",
)
