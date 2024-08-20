package com.chill.mallang.ui.feature.game.game01.SubView.ReviewScreen.Layout

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.R
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.QuokkaRealBrown
import com.chill.mallang.ui.theme.SuperLightGray
import com.chill.mallang.ui.theme.Typography
import kotlinx.coroutines.delay

@Composable
fun RoundResultItem(
    round: Int = 0,
    score: Int = 0,
    delay: Int = 0,
    playPointIndicatorSoundEffect: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        delay(delay.toLong())
        playPointIndicatorSoundEffect()
    }
    Box(
        modifier =
        modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 10.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = stringResource(id = R.string.round_title_format, round),
                style = Typography.displayLarge,
                fontSize = 30.sp,
            )
            ScoreProgressIndicator(
                score = score,
                perfectScore = 100,
                delay = delay,
            )
        }
    }
}

@Composable
fun ScoreProgressIndicator(
    score: Int = 0,
    perfectScore: Int = 100,
    strokeWidth: Float = 5f,
    delay: Int = 1000,
    modifier: Modifier = Modifier,
) {
    val animatedProgress = remember { Animatable(0f) }
    val targetProgress = (score.toFloat() / perfectScore)

    var isTransparent by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (isTransparent) 1.0f else 0.0f,
        animationSpec = tween(durationMillis = 300),
    )

    LaunchedEffect(targetProgress) {
        delay(delay.toLong())
        isTransparent = true
        animatedProgress.animateTo(
            targetProgress,
            animationSpec = tween(durationMillis = 1500),
        )
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier =
        modifier
            .size(100.dp)
            .padding(10.dp)
            .graphicsLayer(alpha = alpha),
    ) {
        CircularProgressIndicator(
            progress = { animatedProgress.value },
            modifier = Modifier.fillMaxSize(),
            strokeWidth = strokeWidth.dp,
            strokeCap = StrokeCap.Round,
            color = QuokkaRealBrown,
            trackColor = SuperLightGray,
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center),
        ) {
            Text(
                text = "${score}점",
                color = QuokkaRealBrown,
                style = Typography.displayLarge,
                fontSize = 30.sp,
            )
            Text(
                text = "/${perfectScore}점",
                style = Typography.displayLarge,
                fontSize = 16.sp,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoundResultItemPreview() {
    MallangTheme {
        RoundResultItem()
    }
}

@Preview(showBackground = true)
@Composable
fun ScoreProgressIndicatorPreview() {
    MallangTheme {
        ScoreProgressIndicator()
    }
}
