package com.chill.mallang.ui.feature.map.state

sealed interface TryCountState {
    data object Empty : TryCountState

    data class HasValue(
        val count: Int,
    ) : TryCountState

    data class Error(
        val message: String? = null,
    ) : TryCountState
}
