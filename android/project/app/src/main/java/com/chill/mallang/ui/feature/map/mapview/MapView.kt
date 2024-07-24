package com.chill.mallang.ui.feature.map.mapview

import CustomMarkerState
import android.Manifest
import android.annotation.SuppressLint
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.chill.mallang.ui.util.MultiplePermissionsHandler
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@SuppressLint("MissingPermission")
@Composable
fun MapView(
    modifier: Modifier = Modifier,
    onLocationPermissionDenied: () -> Unit = {},
    customMarkers: List<CustomMarkerState> = listOf()
){
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var currentLocation by remember { mutableStateOf(MarkerState(LatLng(0.0, 0.0))) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation.position, 10f)
    }
    val uiSettings = remember {
        MapUiSettings(myLocationButtonEnabled = true)
    }
    val properties by remember {
        mutableStateOf(MapProperties(isMyLocationEnabled = true))
    }

    var isMapLoaded by remember {
        mutableStateOf(false)
    }
    var hasPermission by remember {
        mutableStateOf(false)
    }


    MultiplePermissionsHandler(
        permissions = listOf( Manifest.permission.ACCESS_FINE_LOCATION )
    ) { permissionResults ->
        if(permissionResults.all { permissions -> permissions.value }){
            hasPermission = true
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    currentLocation = MarkerState(LatLng(it.latitude, it.longitude))
                    CoroutineScope(Dispatchers.Main).launch {
                        cameraPositionState.animate(
                            CameraUpdateFactory.newCameraPosition(
                                CameraPosition(LatLng(it.latitude, it.longitude), 15f, 0f, 0f)
                            ),
                            1000
                        )
                    }
                }
            }
        } else {
            onLocationPermissionDenied()
        }
    }

    Box {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapLoaded = { isMapLoaded = true },
            uiSettings = uiSettings,
            properties = properties
        ) {
            if(hasPermission && isMapLoaded){
                for (marker : CustomMarkerState in customMarkers){
                    CustomMarkerWithArea(
                        state = marker
                    )
                }
            }
        }
        if(!hasPermission || !isMapLoaded) {
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
