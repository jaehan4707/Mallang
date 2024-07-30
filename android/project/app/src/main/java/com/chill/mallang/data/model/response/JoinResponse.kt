package com.chill.mallang.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JoinResponse(
    val data: JoinData? = null,
    val status: Int? = null,
    val success: String? = null,
)

@Serializable
data class JoinData(
    @SerialName("is_registered")
    val isRegister: Boolean? = false,
    @SerialName("token")
    val token: String? = "",
)
