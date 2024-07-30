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
import com.chill.mallang.data.model.Area
import com.chill.mallang.ui.feature.map.layout.MapScaffold
import com.chill.mallang.ui.feature.map.mapview.MapView
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.util.MultiplePermissionsHandler
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

/**
 * @param onShowAreaDetail 점령지 상세 정보 화면으로 이동
 */
@SuppressLint("MissingPermission")
@Composable
fun Map(
    modifier: Modifier = Modifier,
    onShowAreaDetail: (Area) -> Unit = {}
){
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val (currentLocation, setLocation) = remember { mutableStateOf<LatLng?>(null) }

    val (areas, setArea) = remember { mutableStateOf<AreasState>(AreasState.Empty) }

    var hasPermission by remember {
        mutableStateOf(false)
    }

    MultiplePermissionsHandler(
        permissions = listOf( Manifest.permission.ACCESS_FINE_LOCATION ),
    ) { permissionResults ->
        if(permissionResults.all { permissions -> permissions.value }){
            hasPermission = true

            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                setLocation(LatLng(location.latitude, location.longitude))
            }
        } else {
            // On permission denied
        }
    }

    LaunchedEffect(hasPermission) {
        if(hasPermission){
            // Get area info
            setArea(AreasState.HasValue(listOf()))
        }
    }

    Column(modifier = modifier.fillMaxSize()){
        MapView(
            modifier = Modifier.weight(1f),
            currentLocation = currentLocation,
            areasState = areas
        )
        MapScaffold(
            areaSelected = null,
            currentLocation = null,
            onLocate = {},
            onShowDetail = onShowAreaDetail
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MapPreview(){
    MallangTheme {
        Map()
    }
}

