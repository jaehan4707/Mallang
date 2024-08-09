package com.chill.mallang.ui.feature.fort_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chill.mallang.R
import com.chill.mallang.data.repository.local.DataStoreRepository
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
        private val dataStoreRepository: DataStoreRepository,
    ) : ViewModel() {
        private val _areaDetailStateFlow = MutableStateFlow<AreaDetailState>(AreaDetailState.Loading)
        val areaDetailStateFlow = _areaDetailStateFlow.asStateFlow()

        private val _teamRecordStateFlow = MutableStateFlow<TeamRecordState>(TeamRecordState.Loading)
        val teamRecordStateFlow = _teamRecordStateFlow.asStateFlow()

        private val _tryCountStateFlow = MutableStateFlow<TryCountState>(TryCountState.Loading)
        val tryCountStateFlow = _tryCountStateFlow.asStateFlow()

        fun invalidateData() {
            _areaDetailStateFlow.value =
                AreaDetailState.Error(ErrorMessage.RuntimeError(R.string.invalid_entry))
            _teamRecordStateFlow.value =
                TeamRecordState.Error(ErrorMessage.RuntimeError(R.string.invalid_entry))
            _tryCountStateFlow.value =
                TryCountState.Error(ErrorMessage.RuntimeError(R.string.invalid_entry))
        }

        fun loadTryCount() {
            viewModelScope.launch {
                val userId = dataStoreRepository.getUserId()
                userId?.let { id ->
                    areaRepository
                        .getTryCount(id)
                        .transformToUiState(
                            onSuccess = { TryCountState.Success(it.count) },
                            onError = { TryCountState.Error(ErrorMessage.NetworkError(it)) },
                        )
                        .collect{
                            _tryCountStateFlow.value = it
                        }
                }
            }
        }

        fun loadOccupationState(areaId: Long) {
            viewModelScope.launch {
                val teamId = dataStoreRepository.getFactionId()
                teamId?.let { id ->
                    areaRepository
                        .getAreaDetail(areaId, id)
                        .transformToUiState(
                            onSuccess = { AreaDetailState.Success(it) },
                            onError = { AreaDetailState.Error(ErrorMessage.NetworkError(it)) },
                        ).collect {
                            _areaDetailStateFlow.value = it
                        }
                }
            }
        }

        fun loadTeamLeadersState(areaId: Long) {
            viewModelScope.launch {
                val userId = dataStoreRepository.getUserId()
                userId?.let { id ->
                    areaRepository
                        .getAreaRecords(areaId, id)
                        .transformToUiState(
                            onSuccess = { TeamRecordState.Success(it) },
                            onError = { TeamRecordState.Error(ErrorMessage.NetworkError(it)) },
                        ).collect {
                            _teamRecordStateFlow.value = it
                        }
                }
            }
        }
    }
