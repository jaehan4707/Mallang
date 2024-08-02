package com.chill.mallang.ui.feature.fort_detail

import android.content.Context
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

    @Immutable
    data class Error(
        val errorMessage: ErrorMessage,
    ) : AreaDetailState
}

@Stable
sealed interface TeamRecordState {
    @Immutable
    data object Loading : TeamRecordState

    @Immutable
    data class Success(
        val teamRecords: TeamRecords,
    ) : TeamRecordState

    @Immutable
    data class Error(
        val errorMessage: ErrorMessage,
    ) : TeamRecordState
}

@Stable
sealed interface ErrorMessage {
    @Immutable
    data class NetworkError(
        val errorMessage: String,
    ) : ErrorMessage

    @Immutable
    data class RuntimeError(
        val errorMessageId: Int,
    ) : ErrorMessage

    fun getErrorMessage(context: Context): String {
        return when (this) {
            is NetworkError -> errorMessage
            is RuntimeError -> context.getString(errorMessageId)
        }
    }
}
