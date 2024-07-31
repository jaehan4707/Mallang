package com.chill.mallang.ui.feature.fort_detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FortDetailViewModel
    @Inject
    constructor() : ViewModel() {
        private val _uiState = MutableStateFlow<FortDetailUiState>(FortDetailUiState())
        val uiState = _uiState.asStateFlow()
    }
