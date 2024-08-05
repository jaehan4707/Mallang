package com.chill.mallang.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class SignOutResponse(
    val success: String? = "",
    val status: Int? = 0
)