package com.chill.mallang.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class UpdateNickNameResponse(
    val data: UpdateNickNameData? = null,
    val success: String? = "",
    val status: Int? = 0,
)

@Serializable
data class UpdateNickNameData(
    val data: String? = "",
)
