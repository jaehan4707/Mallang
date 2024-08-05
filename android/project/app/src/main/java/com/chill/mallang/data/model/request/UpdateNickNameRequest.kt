package com.chill.mallang.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateNickNameRequest(
    @SerialName("nickname")
    val nickName: String = "",
)
