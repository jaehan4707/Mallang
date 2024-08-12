package com.chill.mallang.ui.feature.game.game01.SubView.ReviewScreen.Layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.chill.mallang.R
import com.chill.mallang.ui.theme.MallangTheme
import kotlinx.coroutines.delay

@Composable
fun StampBody(
    totalPoint: Float = 0F,
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    alpha: Float = 1.0f
){
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.stamp_background))

    val animationState = rememberLottieAnimatable()

    // 애니메이션 시작 지연
    LaunchedEffect(Unit) {
        delay(6000L)  // 1초(1000ms) 지연
        animationState.animate(composition)
    }

    val stamp_img =
        if(totalPoint >= 200){
            R.drawable.img_a_mark
        } else if(totalPoint >= 150){
            R.drawable.img_b_mark
        } else if(totalPoint >= 100){
            R.drawable.img_c_mark
        } else if(totalPoint >= 50){
            R.drawable.img_d_mark
        } else {
            R.drawable.img_f_mark
        }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = stamp_img),
            contentDescription = "",
            modifier =
            Modifier
                .size(size)
                .graphicsLayer(alpha = alpha),
        )
//        LottieAnimation(
//            composition = composition,
//            progress = animationState.progress,
//            modifier = Modifier.fillMaxWidth(),
//        )
    }
}

@Preview(showBackground = true)
@Composable
fun AStampBodyPreview(){
    MallangTheme {
        StampBody(
            totalPoint = 210F
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BStampBodyPreview(){
    MallangTheme {
        StampBody(
            totalPoint = 190F
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CStampBodyPreview(){
    MallangTheme {
        StampBody(
            totalPoint = 140F
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DStampBodyPreview(){
    MallangTheme {
        StampBody(
            totalPoint = 90F
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FStampBodyPreview(){
    MallangTheme {
        StampBody(
            totalPoint = 20F
        )
    }
}
