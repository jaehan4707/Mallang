package com.chill.mallang.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class JoinResponse(
    val data: Token? = null,
)

@Serializable
data class Token(val token: String? = "")