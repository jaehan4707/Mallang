package com.chill.mallang.ui.feature.game.game01.SubView

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chill.mallang.R
import com.chill.mallang.ui.theme.MallangTheme
import kotlinx.coroutines.delay

@Composable
fun Game01CurtainCallScreen(
    finishGame: () -> Unit = {},
    modifier: Modifier = Modifier,
){
    LaunchedEffect(Unit) {
        delay(2000L)
        finishGame()
    }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(50.dp))
            Text(text = stringResource(id = R.string.game_end_message))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Game01CurtainCallScreenPreview() {
    MallangTheme {
        Game01CurtainCallScreen()
    }
}
