package com.chill.mallang.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JoinRequest(
    @SerialName("token")
    val idToken: String = "",
    @SerialName("email")
    val userEmail: String = "",
    @SerialName("nickname")
    val userNickName: String = "",
    @SerialName("picture")
    val userProfileImageUrl: String = "",
//    @SerializedName("team") 서버쪽 개발이 안끝남
//    val team: String = "",
)
