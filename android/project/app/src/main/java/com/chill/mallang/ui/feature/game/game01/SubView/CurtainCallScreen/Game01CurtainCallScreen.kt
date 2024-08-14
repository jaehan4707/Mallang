package com.chill.mallang.ui.feature.game.game01.SubView.CurtainCallScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.chill.mallang.R
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography
import kotlinx.coroutines.delay

@Composable
fun Game01CurtainCallScreen(
    finishGame: () -> Unit = {},
    modifier: Modifier = Modifier,
){
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.game_splash_background))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
    )

    LaunchedEffect(Unit) {
        delay(2000L)
        finishGame()
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Image(
            painter = painterResource(id = R.drawable.img_game_curtain_call),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = stringResource(id = R.string.game_end_message),
            style = Typography.displayLarge,
            fontSize = 30.sp,
            modifier = modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Game01CurtainCallScreenPreview() {
    MallangTheme {
        Game01CurtainCallScreen()
    }
}
