package com.chill.mallang.ui.feature.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel
    @Inject
    constructor() : ViewModel() {
        private val _uiState = MutableStateFlow<SummaryUiState>(SummaryUiState.Loading)
        val uiState = _uiState.asStateFlow()

        init {
            loadSummary()
        }

        private fun loadSummary() {
            viewModelScope.launch {
                delay(3000L)
                _uiState.value = SummaryUiState.Success
            }
        }
    }
