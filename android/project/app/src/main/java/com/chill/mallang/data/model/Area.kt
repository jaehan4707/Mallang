package com.chill.mallang.data.model

import com.google.android.gms.maps.model.LatLng

data class Area(
    val areaId: Long,
    val areaName: String,
    val latitude: Double,
    val longitude: Double
){
    val latLng = LatLng(latitude, longitude)
}
