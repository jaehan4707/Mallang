package com.chill.mallang.ui.feature.game.game01.SubView

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.R
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography
import kotlinx.coroutines.delay

@Composable
fun Game01RoundScreen(
    round: Int,
    completeRoundLoad: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isTransparent by remember { mutableStateOf(false) }

    val size by animateDpAsState(
        targetValue = if (isExpanded) 250.dp else 400.dp,
        animationSpec = tween(durationMillis = 1000)
    )

    val alpha by animateFloatAsState(
        targetValue = if (isTransparent) 1.0f else 0.0f,
        animationSpec = tween(durationMillis = 1000),
    )

    LaunchedEffect(Unit) {
        isExpanded = true
        isTransparent = true
        delay(2000L)
        completeRoundLoad()
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_round_loading),
                contentDescription = "말랑",
                modifier = modifier
                    .size(size)
                    .graphicsLayer(alpha = alpha),
            )
            Text(
                text = stringResource(id = R.string.round_title_format, round),
                style = Typography.headlineLarge,
                fontSize = 40.sp,
                modifier = modifier
                    .graphicsLayer(alpha = alpha)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Game01RoundScreenPreview() {
    MallangTheme {
        Game01RoundScreen(round = 1, {})
    }
}
