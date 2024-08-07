package com.chill.mallang.ui.feature.fort_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chill.mallang.R
import com.chill.mallang.data.repository.remote.AreaRepository
import com.chill.mallang.ui.util.transformToUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FortDetailViewModel
    @Inject
    constructor(
        private val savedStateHandle: SavedStateHandle,
        private val areaRepository: AreaRepository,
    ) : ViewModel() {
        private val _areaDetailStateFlow = MutableStateFlow<AreaDetailState>(AreaDetailState.Loading)
        val areaDetailStateFlow = _areaDetailStateFlow.asStateFlow()

        private val _teamRecordStateFlow = MutableStateFlow<TeamRecordState>(TeamRecordState.Loading)
        val teamRecordStateFlow = _teamRecordStateFlow.asStateFlow()

        fun invalidateData() {
            _areaDetailStateFlow.value =
                AreaDetailState.Error(ErrorMessage.RuntimeError(R.string.invalid_entry))
            _teamRecordStateFlow.value =
                TeamRecordState.Error(ErrorMessage.RuntimeError(R.string.invalid_entry))
        }

        fun loadOccupationState(
            areaId: Int,
            userTeam: Int,
        ) {
            viewModelScope.launch {
                areaRepository
                    .getAreaDetail(areaId, userTeam)
                    .transformToUiState(
                        onSuccess = { AreaDetailState.Success(it) },
                        onError = { AreaDetailState.Error(ErrorMessage.NetworkError(it)) },
                    ).collect {
                        _areaDetailStateFlow.value = it
                    }
            }
        }

        fun loadTeamLeadersState(
            areaId: Int,
            userId: Int,
        ) {
            viewModelScope.launch {
                areaRepository
                    .getAreaRecords(areaId, userId)
                    .transformToUiState(
                        onSuccess = { TeamRecordState.Success(it) },
                        onError = { TeamRecordState.Error(ErrorMessage.NetworkError(it)) },
                    ).collect {
                        _teamRecordStateFlow.value = it
                    }
            }
        }
    }
