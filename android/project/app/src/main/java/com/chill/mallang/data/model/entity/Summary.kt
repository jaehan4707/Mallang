package com.chill.mallang.data.model.entity

data class Summary(
    val areaName: String = "",
    val malScore: Double = 0.0,
    val langScore: Double = 0.0,
    val topUserNickName: String = "",
    val topScore: Double = 0.0,
    val victoryFactionId: Int = 0,
)
