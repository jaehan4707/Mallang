package com.chill.mallang.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetUserInfoResponse(
    val data: UserInfo? = null,
    val success: String? = null,
    val status: Int? = null,
)

@Serializable
data class UserInfo(
    @SerialName("nickname")
    val nickName: String? = null,
    @SerialName("faction")
    val faction: String? = null,
    @SerialName("try_count")
    val tryCount: Int? = null,
)
