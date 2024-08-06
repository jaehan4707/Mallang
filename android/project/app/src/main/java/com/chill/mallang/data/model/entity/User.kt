package com.chill.mallang.data.model.entity

data class User(
    val id: Int = 0,
    val email: String = "",
    val factionId: Int = 0,
    val tryCount: Int = 0,
    val nickName: String = "",
)
