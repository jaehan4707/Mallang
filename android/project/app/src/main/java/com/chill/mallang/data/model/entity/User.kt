package com.chill.mallang.data.model.entity

data class User(
    val id: Long = 0,
    val email: String = "",
    val factionId: Long = 0,
    val tryCount: Int = 0,
    val nickName: String = "",
    val level: Int = 0,
    val exp: Float = 0f,
)
