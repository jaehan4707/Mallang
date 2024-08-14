package com.chill.mallang.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JoinRequest(
    @SerialName("email")
    val userEmail: String = "",
    @SerialName("nickname")
    val userNickName: String = "",
    @SerialName("picture")
    val userProfileImageUrl: String = "",
    @SerialName("faction")
    val team: String = "",
)
