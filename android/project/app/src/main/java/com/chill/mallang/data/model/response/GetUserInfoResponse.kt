package com.chill.mallang.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetUserInfoResponse(
    @SerialName("nickname")
    val nickName: String? = null,
    @SerialName("faction")
    val faction: String? = null,
    @SerialName("try_count")
    val tryCount: Int? = null,
)
