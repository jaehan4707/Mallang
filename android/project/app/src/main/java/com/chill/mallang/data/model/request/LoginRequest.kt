package com.chill.mallang.data.model.request

data class LoginRequest(
    val idToken: String = "",
    val email: String = "",
)