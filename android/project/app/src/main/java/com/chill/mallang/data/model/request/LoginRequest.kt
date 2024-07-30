package com.chill.mallang.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("token")
    val idToken: String = "",
    @SerialName("email")
    val email: String = "",
)