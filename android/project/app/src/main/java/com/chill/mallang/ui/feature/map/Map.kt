package com.chill.mallang.ui.feature.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chill.mallang.R
import com.chill.mallang.ui.feature.map.layout.MapScaffold
import com.chill.mallang.ui.feature.map.mapview.MapMarker
import com.chill.mallang.ui.theme.MallangTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun Map(
    modifier: Modifier = Modifier,
){
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }

    Column(modifier = modifier.fillMaxSize()){
        GoogleMap(
            modifier = Modifier.weight(1f),
            cameraPositionState = cameraPositionState
        ) {
            MapMarker(
                state = MarkerState(position = singapore),
                title = "Singapore",
                snippet = "Marker in Singapore",
                iconResourceId = R.drawable.ic_location
            )
        }
        MapScaffold()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MapPreview(){
    MallangTheme {
        Map()
    }
}

