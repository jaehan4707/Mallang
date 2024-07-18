package com.chill.mallang.ui.feature.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chill.mallang.ui.component.PercentageBar
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Red01
import com.chill.mallang.ui.theme.SkyBlue

/**
 * 지도 위에 올라가는 레이아웃
 * 기본적으로 Scaffold이기 때문에 전체화면이다.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScaffold(
    modifier: Modifier = Modifier
){
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        modifier = modifier.background(color = Color.Transparent),
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 300.dp,
        sheetSwipeEnabled = false,
        containerColor = Color.Transparent,
        sheetContent = {
            Text("점령지 목록", modifier = Modifier
                .padding(bottom = 26.dp)
                .align(Alignment.CenterHorizontally))
            LocationList(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .height(200.dp)
            )
        }
    ) {
        Column {
            PercentageBar(
                modifier = Modifier.padding(top = 16.dp),
                leftPercentage = 30,
                rightPercentage = 70,
                leftLabel = "",
                rightLabel = "",
                leftColor = Red01,
                rightColor = SkyBlue
            )
            Spacer(modifier = Modifier.weight(1f))
            ChallengeMessageBar(
                modifier = Modifier.padding(bottom = 320.dp)
            )
        }
    }
}

@Preview(apiLevel = 34)
@Composable
private fun MapScaffoldPreview(){
    MallangTheme {
        MapScaffold()
    }
}