package com.chill.mallang.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val data: LoginData? = null,
    val success: String? = null,
    val code: Int? = null,
)

@Serializable
data class LoginData(
    @SerialName("is_registered")
    val isRegister: Boolean? = false,
    @SerialName("token")
    val token: String? = "",
)