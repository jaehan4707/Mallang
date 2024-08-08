package com.chill.mallang.data.model.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TryCount(
    @SerialName("try_count") val count: Int
)
