package com.chill.mallang.ui.feature.fort_detail

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.chill.mallang.data.model.entity.AreaDetail
import com.chill.mallang.data.model.entity.TeamRecords

@Stable
sealed interface AreaDetailState {
    @Immutable
    data object Loading : AreaDetailState

    @Immutable
    data class Success(
        val areaDetail: AreaDetail,
    ) : AreaDetailState

    data class Error(
        val errorMessage: String,
    ) : AreaDetailState
}

@Stable
sealed interface TeamRecordState {
    data object Loading : TeamRecordState

    data class Success(
        val teamRecords: TeamRecords,
    ) : TeamRecordState

    data class Error(
        val errorMessage: String,
    ) : TeamRecordState
}
