package com.chill.mallang.ui.feature.game.game01.SubView

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.chill.mallang.R
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography

@Composable
fun Game01SplashScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_game_splash),
            contentDescription = "",
            modifier = modifier.fillMaxSize(),
        )
        Text(
            text = "Loading...",
            style = Typography.displayLarge,
            fontSize = 30.sp,
            modifier = modifier.align(Alignment.BottomCenter),
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Game01SplashScreenPreview() {
    MallangTheme {
        Game01SplashScreen()
    }
}
