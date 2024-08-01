package com.chill.mallang.ui.feature.map

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.chill.mallang.data.model.entity.Area
import com.chill.mallang.ui.theme.Red01
import com.google.maps.android.compose.MarkerState

class CustomMarkerState(
    val area: Area,
) {
    val marker = MarkerState(area.latLng)

    var distance by mutableIntStateOf(0)

    var color by mutableStateOf(Red01)

    var radius by mutableDoubleStateOf(10.0)
}
