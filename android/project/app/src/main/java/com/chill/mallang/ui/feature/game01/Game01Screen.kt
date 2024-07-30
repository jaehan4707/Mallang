package com.chill.mallang.ui.feature.game01

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun Game01Screen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun GameLobbyScreenPreview() {
    MallangTheme {
        Game01Screen()
    }
}
