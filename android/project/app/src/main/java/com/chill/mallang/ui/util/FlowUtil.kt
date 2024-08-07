package com.chill.mallang.ui.util

import com.chill.mallang.data.model.response.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

fun <T, UIState> Flow<ApiResponse<T>>.transformToUiState(
    onSuccess: (T) -> UIState,
    onError: (String) -> UIState,
): Flow<UIState> =
    transform { response ->
        when (response) {
            is ApiResponse.Success -> response.body?.let { emit(onSuccess(it)) }
            is ApiResponse.Error -> emit(onError(response.errorMessage))
            else -> {
                emit(onError("Unknown error"))
            }
        }
    }
