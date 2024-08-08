package com.chill.mallang.ui.feature.map

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chill.mallang.data.model.entity.Area
import com.chill.mallang.data.model.entity.TeamList
import com.chill.mallang.ui.feature.map.layout.MapScaffold
import com.chill.mallang.ui.feature.map.mapview.MapView
import com.chill.mallang.ui.feature.map.state.ProximityState
import com.chill.mallang.ui.feature.topbar.TopbarHandler
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.util.MultiplePermissionsHandler
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

/**
 * @param onShowAreaDetail 점령지 상세 정보 화면으로 이동
 */
@SuppressLint("MissingPermission")
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    onShowAreaDetail: (Area) -> Unit = {},
) {
    val context = LocalContext.current
    val viewModel: MapViewModel = hiltViewModel()
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    val currentLocation by viewModel.currentLocation.collectAsStateWithLifecycle()
    val areas by viewModel.areaState.collectAsStateWithLifecycle()
    val status by viewModel.statusState.collectAsStateWithLifecycle()
    val proximityState = viewModel.proximityState
    val selectedArea = viewModel.selectedArea

    var hasPermission by remember { mutableStateOf(false) }

    MultiplePermissionsHandler(
        permissions = listOf(Manifest.permission.ACCESS_FINE_LOCATION),
    ) { permissionResults ->
        if (permissionResults.all { permissions -> permissions.value }) {
            hasPermission = true

            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                viewModel.setLocation(LatLng(location.latitude, location.longitude))
            }
        } else {
            // On permission denied
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadStatus()
    }

    LaunchedEffect(hasPermission) {
        if (hasPermission) {
            // Get area info
            viewModel.loadAreas()
        }
    }

    TopbarHandler(isVisible = true)

    MapScreenContent(
        currentLocation = currentLocation,
        areasState = areas,
        selectedArea = selectedArea,
        status = status,
        proximityState = proximityState,
        onSelectArea = viewModel::setToSelected,
        onLocate = viewModel::findClosestArea,
        onShowAreaDetail = onShowAreaDetail,
        onCameraMove = viewModel::resetSelected,
    )
}

@Composable
fun MapScreenContent(
    modifier: Modifier = Modifier,
    currentLocation: LocationState,
    areasState: AreasState,
    proximityState: ProximityState,
    status: TeamList,
    selectedArea: Area?,
    onSelectArea: (Area) -> Unit = {},
    onLocate: () -> Unit = {},
    onShowAreaDetail: (Area) -> Unit = {},
    onCameraMove: () -> Unit = {},
) {
    Column(modifier = modifier.fillMaxSize()) {
        MapView(
            modifier = Modifier.weight(1f),
            currentLocation = currentLocation,
            selectedArea = selectedArea,
            areasState = areasState,
            onSelectArea = onSelectArea,
            onCameraMove = onCameraMove,
        )
        MapScaffold(
            areaSelected = selectedArea,
            currentLocation = currentLocation,
            status = status,
            onLocate = onLocate,
            onShowDetail = onShowAreaDetail,
            proximityState = proximityState
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MapPreview() {
    MallangTheme {
        MapScreenContent(
            currentLocation = LocationState.Empty,
            areasState = AreasState.Empty,
            status = TeamList(listOf()),
            selectedArea = null,
            proximityState = ProximityState.FarAway,
        )
    }
}
