package com.chill.mallang.ui.feature.map

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.chill.mallang.data.model.Area

@Stable
sealed interface AreasState {

    @Immutable
    data object Empty : AreasState

    @Immutable
    data class HasValue(
        val list: List<Area> = listOf()
    ) : AreasState

    @Immutable
    data class Error(
        val errorMessage: String = ""
    ) : AreasState
}