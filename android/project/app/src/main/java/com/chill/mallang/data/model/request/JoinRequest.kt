package com.chill.mallang.data.model.request

import com.google.gson.annotations.SerializedName

data class JoinRequest(
    @SerializedName("email")
    val userEmail: String = "",
    @SerializedName("nickName")
    val userNickName: String = "",
    @SerializedName("picture")
    val userProfileImageUrl: String = "",
    @SerializedName("try_count")
    val tryCount: Int = 0,
    @SerializedName("team")
    val team: String = "",
)
