package com.chill.mallang.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class UpdateNickNameResponse(
    val data: String? = null,
)
