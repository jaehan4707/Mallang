package com.chill.mallang.ui.feature.map.state

sealed interface ProximityState {
    data object FarAway : ProximityState

    data class Distant(
        val distance: Int,
    ) : ProximityState

    data class Adjacent(
        val distance: Int,
    ) : ProximityState
}
