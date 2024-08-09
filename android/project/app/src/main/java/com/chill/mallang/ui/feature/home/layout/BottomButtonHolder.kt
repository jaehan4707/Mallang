package com.chill.mallang.ui.feature.home.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun BottomButtonHolder(
    modifier: Modifier = Modifier,
    onClickStudy: () -> Unit = {},
    onClickMap: () -> Unit = {},
    onClickSetting: () -> Unit = {},
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .height(210.dp)) {
        SettingsButton(
            modifier = Modifier
                .padding(8.dp)
                .size(40.dp)
                .align(Alignment.TopEnd),
            onClick = onClickSetting,
        )
        Row(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            StudyButton(onClick = onClickStudy)
            MapButton(onClick = onClickMap)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BottomButtonHolderPreview() {
    MallangTheme {
        BottomButtonHolder()
    }
}
