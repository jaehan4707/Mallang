package com.chill.mallang.ui.feature.map

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chill.mallang.R
import com.chill.mallang.data.model.entity.Area
import com.chill.mallang.data.model.entity.TeamList
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.remote.AreaRepository
import com.chill.mallang.ui.feature.map.MapDistance.inArea
import com.chill.mallang.ui.feature.map.state.ProximityState
import com.chill.mallang.ui.feature.map.state.TryCountState
import com.chill.mallang.ui.sound.SoundManager
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
        private val soundManager: SoundManager,
    ) : ViewModel() {
        private val _currentLocation = MutableStateFlow<LocationState>(LocationState.Empty)
        val currentLocation: StateFlow<LocationState> = _currentLocation

        private val _areaState = MutableStateFlow<AreasState>(AreasState.Empty)
        val areaState: StateFlow<AreasState> = _areaState

        private val _statusState = MutableStateFlow<TeamList>(TeamList(listOf()))
        val statusState: StateFlow<TeamList> = _statusState

        private val _tryCountState = MutableStateFlow<TryCountState>(TryCountState.Empty)
        val tryCountState: StateFlow<TryCountState> = _tryCountState

        var proximityState by mutableStateOf<ProximityState>(ProximityState.FarAway)
            private set

        var selectedArea by mutableStateOf<Area?>(null)
            private set

        fun playBGM() {
            soundManager.playBackgroundMusic(R.raw.bgm_map)
        }

        fun stopBGM() {
            soundManager.stopBackgroundMusic(R.raw.bgm_map)
        }

        fun setLocation(latLng: LatLng) {
            _currentLocation.update {
                LocationState.Tracking(latLng)
            }

            proximityState =
                if (selectedArea != null) {
                    val distance =
                        SphericalUtil
                            .computeDistanceBetween(
                                selectedArea!!.latLng,
                                latLng,
                            ).toInt()
                    if (distance < inArea) {
                        ProximityState.Adjacent(distance)
                    } else {
                        ProximityState.Distant(distance)
                    }
                } else {
                    getProximityClosestArea(latLng)
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

        fun setToSelected(area: Area) {
            selectedArea = area

            proximityState =
                if (currentLocation.value is LocationState.Tracking) {
                    val distance =
                        SphericalUtil
                            .computeDistanceBetween(
                                selectedArea!!.latLng,
                                (currentLocation.value as LocationState.Tracking).latLng,
                            ).toInt()
                    if (distance < inArea) {
                        ProximityState.Adjacent(distance)
                    } else {
                        ProximityState.Distant(distance)
                    }
                } else {
                    ProximityState.FarAway
                }
        }

        fun resetSelected() {
            selectedArea = null
            if (currentLocation.value is LocationState.Tracking) {
                val latLng = (currentLocation.value as LocationState.Tracking).latLng
                proximityState = getProximityClosestArea(latLng)
            }
        }

        /**
         * 현재 가진 점령지 목록 중에서 현 위치와 가장 가까운 점령지 선택
         */
        fun findClosestArea() {
            viewModelScope.launch {
                if (currentLocation.value is LocationState.Tracking) {
                    val closest =
                        getClosestArea((currentLocation.value as LocationState.Tracking).latLng)
                    if (closest != null) {
                        setToSelected(closest)
                    }
                }
            }
        }

        private fun getClosestArea(latLng: LatLng): Area? =
            if (areaState.value is AreasState.HasValue) {
                val areas = areaState.value as AreasState.HasValue

                areas.list.minByOrNull { area ->
                    SphericalUtil.computeDistanceBetween(
                        area.latLng,
                        latLng,
                    )
                }
            } else {
                null
            }

        private fun getProximityClosestArea(latLng: LatLng): ProximityState {
            val closest = getClosestArea(latLng)
            if (closest != null) {
                val distance =
                    SphericalUtil.computeDistanceBetween(closest.latLng, latLng).toInt()
                if (distance < inArea) {
                    selectedArea = closest
                    return ProximityState.Adjacent(distance)
                } else {
                    return ProximityState.FarAway
                }
            } else {
                return ProximityState.FarAway
            }
        }
    }

object MapDistance {
    const val inArea = 300
}
