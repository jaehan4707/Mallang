package com.chill.mallang.ui.feature.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.remote.AreaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel
    @Inject
    constructor(
        private val areaRepository: AreaRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<SummaryUiState>(SummaryUiState.Loading)
        val uiState = _uiState.asStateFlow()

        fun reloadSummary() {
            _uiState.value = SummaryUiState.Loading
            loadSummary()
        }

        fun loadSummary() {
            viewModelScope.launch {
                delay(3000L)
                areaRepository.getDailySummary().collectLatest { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            _uiState.value =
                                SummaryUiState.Success(
                                    summaryRecords =
                                        response.body?.toPersistentList()
                                            ?: persistentListOf(),
                                )
                        }

                        is ApiResponse.Error -> {
                            _uiState.value = SummaryUiState.Error(errorMessage = response.errorMessage)
                        }

                        else -> {}
                    }
                }
            }
        }
    }
