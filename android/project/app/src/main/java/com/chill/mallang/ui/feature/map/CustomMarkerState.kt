package com.chill.mallang.ui.feature.map

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import com.chill.mallang.data.model.entity.Area
import com.chill.mallang.ui.feature.map.MapDistance.inArea
import com.google.maps.android.compose.MarkerState

class CustomMarkerState(
    val area: Area,
) {
    val marker = MarkerState(area.latLng)

    var distance by mutableIntStateOf(0)

    var occupyingTeamId by mutableLongStateOf(1L)

    var radius by mutableDoubleStateOf(inArea.toDouble())
}
