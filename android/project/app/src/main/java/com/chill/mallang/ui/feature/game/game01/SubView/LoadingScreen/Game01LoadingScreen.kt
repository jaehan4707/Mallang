package com.chill.mallang.ui.feature.game.game01.SubView.LoadingScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chill.mallang.R
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun Game01LoadingScreen(
    loadingMessage: String = stringResource(id = R.string.loading_message_default),
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(50.dp))
            Text(text = loadingMessage)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Game01LoadingScreenPreview() {
    MallangTheme {
        Game01LoadingScreen()
    }
}
