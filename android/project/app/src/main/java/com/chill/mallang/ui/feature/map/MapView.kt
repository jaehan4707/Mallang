package com.chill.mallang.ui.feature.map

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
import com.chill.mallang.R
import com.chill.mallang.ui.util.MultiplePermissionsHandler
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("MissingPermission")
@Composable
fun MapView(
    modifier: Modifier = Modifier,
    fusedLocationProvider: FusedLocationProviderClient,
    onLocationPermissionDenied: () -> Unit = {}
){
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    var currentLocation by remember { mutableStateOf(MarkerState(LatLng(0.0, 0.0))) }

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
            fusedLocationProvider.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    currentLocation.position = LatLng(it.latitude, it.longitude)
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
            onMapLoaded = { isMapLoaded = true }
        ) {
            Marker(
                state = currentLocation
            )
            MapMarker(
                state = MarkerState(position = singapore),
                title = "Singapore",
                snippet = "Marker in Singapore",
                iconResourceId = R.drawable.ic_location
            )
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
