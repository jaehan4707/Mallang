package com.chill.mallang.ui.feature.map.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chill.mallang.ui.theme.BackGround
import com.chill.mallang.ui.theme.MallangTheme

/**
 * 지도 아래 레이아웃
 */
@Composable
fun MapScaffold(
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(330.dp)
            .background(color = BackGround),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OccupationStatusBar(
            modifier = Modifier.padding(16.dp),
            leftCount = 7,
            rightCount = 3
        )
        CharacterMessageBox(modifier = Modifier
            .weight(1f)
            .padding(horizontal = 16.dp))
        LocateNearbyButton(modifier = Modifier.padding(bottom = 16.dp))
    }
}

@Preview(apiLevel = 34)
@Composable
private fun MapScaffoldPreview(){
    MallangTheme {
        MapScaffold()
    }
}