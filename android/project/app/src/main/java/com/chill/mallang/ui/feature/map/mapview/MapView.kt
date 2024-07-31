package com.chill.mallang.ui.feature.map.mapview

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chill.mallang.data.model.Area
import com.chill.mallang.ui.feature.map.AreasState
import com.chill.mallang.ui.feature.map.CustomMarkerState
import com.chill.mallang.ui.feature.map.LocationState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.SphericalUtil
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapView(
    modifier: Modifier = Modifier,
    currentLocation: LocationState,
    selectedArea: Area?,
    areasState: AreasState,
    onSelectArea: (Area) -> Unit = {}
){
    val cameraPositionState = rememberCameraPositionState()
    val uiSettings = remember {
        MapUiSettings(myLocationButtonEnabled = true)
    }
    val properties by remember {
        mutableStateOf(MapProperties(isMyLocationEnabled = true))
    }
    var isMapLoaded by remember {
        mutableStateOf(false)
    }
    val (markerStates, setMarkers) = remember {
        mutableStateOf(listOf<CustomMarkerState>())
    }

    // 현재 위치가 바뀌면 카메라를 현 위치로 이동
    LaunchedEffect(currentLocation) {
        if(currentLocation is LocationState.Tracking) {
            cameraPositionState.animate(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition(currentLocation.latLng, 15f, 0f, 0f)
                ),
                1000
            )

            for (marker : CustomMarkerState in markerStates){
                marker.distance = SphericalUtil.computeDistanceBetween(currentLocation.latLng, marker.area.latLng).toInt()
            }
        }
    }

    // 마커 선택 시에 카메라를 마커로 이동
    LaunchedEffect(selectedArea) {
        if(selectedArea != null){
            cameraPositionState.animate(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition(selectedArea.latLng, 15f, 0f, 0f)
                ),
                1000
            )
        }
    }

    LaunchedEffect(areasState) {
        if(areasState is AreasState.HasValue){
            setMarkers(areasState.list.map { area -> CustomMarkerState(area) })
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        GoogleMap(
            cameraPositionState = cameraPositionState,
            onMapLoaded = { isMapLoaded = true },
            uiSettings = uiSettings,
            properties = properties
        ) {
            if(isMapLoaded ){
                for (marker : CustomMarkerState in markerStates){
                    key(keys = arrayOf(marker.area.areaId, marker.distance)) {
                        CustomMarkerWithArea(
                            state = marker,
                            onClick = onSelectArea
                        )
                    }
                }
            }
        }
        if(!isMapLoaded) {
            AnimatedVisibility(
                modifier = Modifier
                    .matchParentSize(),
                visible = !isMapLoaded,
                enter = EnterTransition.None,
                exit = fadeOut()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .wrapContentSize()
                )
            }
        }
    }
}
