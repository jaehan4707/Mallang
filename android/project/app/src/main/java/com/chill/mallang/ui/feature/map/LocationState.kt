package com.chill.mallang.ui.feature.map

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.google.android.gms.maps.model.LatLng

@Stable
sealed interface LocationState {

    @Immutable
    data object Empty : LocationState

    @Immutable
    data class Tracking(
        val latLng: LatLng
    ) : LocationState
}