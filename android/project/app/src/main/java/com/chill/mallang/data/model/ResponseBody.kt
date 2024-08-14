package com.chill.mallang.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseBody<T>(
    val data: T?,
)
