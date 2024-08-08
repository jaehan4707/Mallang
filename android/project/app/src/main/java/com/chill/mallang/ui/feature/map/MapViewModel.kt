package com.chill.mallang.ui.feature.map

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chill.mallang.data.model.entity.Area
import com.chill.mallang.data.model.entity.TeamList
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.remote.AreaRepository
import com.chill.mallang.ui.feature.map.state.TryCountState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel
    @Inject
    constructor(
        private val savedStateHandle: SavedStateHandle,
        private val areaRepository: AreaRepository,
    ) : ViewModel() {
        private val _currentLocation = MutableStateFlow<LocationState>(LocationState.Empty)
        val currentLocation: StateFlow<LocationState> = _currentLocation

        private val _areaState = MutableStateFlow<AreasState>(AreasState.Empty)
        val areaState: StateFlow<AreasState> = _areaState

        private val _statusState = MutableStateFlow<TeamList>(TeamList(listOf()))
        val statusState: StateFlow<TeamList> = _statusState

        private val _tryCountState = MutableStateFlow<TryCountState>(TryCountState.Empty)
        val tryCountState: StateFlow<TryCountState> = _tryCountState

        var selectedArea by mutableStateOf<Area?>(null)
            private set

        fun setLocation(latlng: LatLng) {
            _currentLocation.update {
                LocationState.Tracking(latlng)
            }
        }

        fun loadAreas() {
            viewModelScope.launch {
                areaRepository.getAreas().collectLatest { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            _areaState.emit(
                                AreasState.HasValue(
                                    response.body ?: listOf(),
                                ),
                            )
                        }

                        is ApiResponse.Error -> _areaState.emit(AreasState.Error(response.errorMessage))
                        ApiResponse.Init -> {}
                    }
                }
            }
        }

        fun loadStatus() {
            viewModelScope.launch {
                areaRepository.getOccupationStatus().collectLatest { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            _statusState.emit(
                                response.body ?: TeamList(listOf()),
                            )
                        }

                        is ApiResponse.Error -> _areaState.emit(AreasState.Error(response.errorMessage))
                        ApiResponse.Init -> {}
                    }
                }
            }
        }

        fun loadTryCount(areaId: Int) {
            viewModelScope.launch {
                areaRepository.getTryCount(areaId).collectLatest { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            if (response.body != null) {
                                _tryCountState.emit(TryCountState.HasValue(response.body.count))
                            } else {
                                _tryCountState.emit(TryCountState.Error())
                            }
                        }
                        is ApiResponse.Error -> _tryCountState.emit(TryCountState.Error(response.errorMessage))
                        else -> {}
                    }
                }
            }
        }

        fun setToSelected(area: Area) {
            selectedArea = area
        }

        fun resetSelected() {
            selectedArea = null
        }

        /**
         * 현재 가진 점령지 목록 중에서 현 위치와 가장 가까운 점령지 선택
         */
        fun findClosestArea() {
            viewModelScope.launch {
                if (areaState.value is AreasState.HasValue && currentLocation.value is LocationState.Tracking) {
                    val areas = areaState.value as AreasState.HasValue
                    val location = currentLocation.value as LocationState.Tracking

                    val closest =
                        areas.list.minByOrNull { area ->
                            SphericalUtil.computeDistanceBetween(
                                area.latLng,
                                location.latLng,
                            )
                        }

                    if (closest != null) {
                        selectedArea = closest
                    }
                }
            }
        }
    }
