package com.chill.mallang.ui.feature.game.game01.SubView

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.chill.mallang.ui.component.LongBlackButton
import com.chill.mallang.ui.component.experiencebar.AnimatedGaugeBar
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography
import kotlinx.coroutines.delay

@Composable
fun Game01RewardScreen(
    completeReward: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isBadgeSlide by remember { mutableStateOf(false) }
    var isBadgeTransparent by remember { mutableStateOf(false) }
    var isMessageTransparent by remember { mutableStateOf(false) }

    val badgePaddingSize by animateDpAsState(
        targetValue = if (isBadgeSlide) 0.dp else 100.dp,
        animationSpec = tween(durationMillis = 1000),
    )

    val badgeAlpha by animateFloatAsState(
        targetValue = if (isBadgeTransparent) 1.0f else 0.0f,
        animationSpec = tween(durationMillis = 1000),
    )

    val messageAlpha by animateFloatAsState(
        targetValue = if (isMessageTransparent) 1.0f else 0.0f,
        animationSpec = tween(durationMillis = 1000),
    )

    LaunchedEffect(Unit) {
        delay(2000L)
        isBadgeSlide = true
        isBadgeTransparent = true
        delay(1500L)
        isMessageTransparent = true
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 20.dp)
    ){
        Column(
            modifier = modifier.fillMaxSize()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "승급 !",
                style = Typography.displayLarge,
                fontSize = 40.sp,
                modifier = modifier
                    .padding(top = 100.dp)
                    .graphicsLayer(alpha = messageAlpha)
            )
            Box(
                modifier = modifier
                    .size(350.dp)
            ){
                Image(
                    modifier = Modifier
                        .size(250.dp)
                        .padding(top = badgePaddingSize)
                        .align(Alignment.Center)
                        .graphicsLayer(alpha = badgeAlpha),
                    painter =
                    painterResource(id = R.drawable.img_badge_5),
                    contentDescription = "",
                )
            }
            Text(
                "골드 1",
                style = Typography.titleLarge,
                fontSize = 30.sp,
                modifier = modifier.graphicsLayer(alpha = badgeAlpha)
            )
            Spacer(modifier = Modifier.size(80.dp))
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(horizontal = 40.dp, vertical = 80.dp)
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "21",
                    style = Typography.displayLarge,
                    fontSize = 40.sp
                )
                Text("EXP+135")
                Text(
                    text = "22",
                    style = Typography.displayLarge,
                    fontSize = 40.sp
                )
            }
            AnimatedGaugeBar(
                modifier = modifier.padding(top = 10.dp),
                initialValue = 0f,
                targetValue = 0.7f
            )
        }
        LongBlackButton(
            onClick = { completeReward() },
            text = stringResource(id = R.string.game_confirm),
            modifier = modifier.align(Alignment.BottomCenter)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Game01RewardScreenPreview() {
    MallangTheme {
        Game01RewardScreen()
    }
}
