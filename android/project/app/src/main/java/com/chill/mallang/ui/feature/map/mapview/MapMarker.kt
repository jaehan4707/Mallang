package com.chill.mallang.ui.feature.map.mapview

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.chill.mallang.R
import com.chill.mallang.data.model.entity.Area
import com.chill.mallang.ui.feature.map.CustomMarkerState
import com.chill.mallang.ui.theme.BackGround
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState

@Composable
@GoogleMapComposable
fun CustomMarkerWithArea(
    state: CustomMarkerState,
    onClick: (Area) -> Unit = {},
) {
    MarkerComposable(
        state = state.marker,
        onClick = {
            onClick(state.area)
            false
        },
    ) {
        CustomMarker(distance = state.distance, occupyingTeamId = state.occupyingTeamId)
    }
    Circle(
        center = state.marker.position,
        radius = state.radius,
    )
}

@Composable
fun CustomMarker(
    distance: Int,
    occupyingTeamId: Long,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Surface(
            shape = RoundedCornerShape(100.dp),
            border = BorderStroke(2.dp, BackGround),
            color = Gray6,
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                text = "${distance}m",
                color = BackGround,
            )
        }
        Image(
            modifier = Modifier.padding(top = 8.dp).size(56.dp).offset(x = 10.dp),
            painter = painterResource(id = if(occupyingTeamId == 1L) R.drawable.img_mal_mark else R.drawable.img_lang_mark),
            contentDescription = "",
        )
    }
}

/**
 * 비트맵 이미지 마커
 */
@Composable
fun MapMarker(
    state: MarkerState,
    title: String,
    snippet: String,
    @DrawableRes iconResourceId: Int,
) {
    val icon =
        bitmapDescriptor(
            LocalContext.current,
            iconResourceId,
        )
    Marker(
        state = state,
        title = title,
        snippet = snippet,
        icon = icon,
    )
}

/**
 * Drawable to BitmapDescriptor
 */
fun bitmapDescriptor(
    context: Context,
    vectorResId: Int,
): BitmapDescriptor? {
    // Drawable 가져오기
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm =
        Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888,
        )

    // 비트맵 변환
    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}

@Preview
@Composable
fun CustomMarkerPreview() {
    MallangTheme {
        CustomMarker(distance = 100, occupyingTeamId = 1L)
    }
}
