package com.chill.mallang.data.model.entity

import androidx.compose.runtime.Immutable
import com.google.android.gms.maps.model.LatLng
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@Immutable
data class Area(
    val areaId: Long,
    val areaName: String,
    val latitude: Double,
    val longitude: Double
) {
    @Transient
    val latLng = LatLng(latitude, longitude)
}
