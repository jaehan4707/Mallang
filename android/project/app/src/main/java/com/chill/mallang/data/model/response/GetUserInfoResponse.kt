package com.chill.mallang.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetUserInfoResponse(
    @SerialName("id")
    val userId: Int? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("nickname")
    val nickName: String? = null,
    @SerialName("faction_id")
    val faction: Int? = null,
    @SerialName("try_count")
    val tryCount: Int? = null,
)
