package com.chill.mallang.ui.feature.game.game01.SubView.RewardScreen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.chill.mallang.R
import com.chill.mallang.ui.component.LongBlackButton
import com.chill.mallang.ui.component.experiencebar.AnimatedGaugeBar
import com.chill.mallang.ui.component.experiencebar.BigBadge
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography
import kotlinx.coroutines.delay

@Composable
fun Game01RewardScreen(
    userExp: Float = 0F,
    userGameScore: Float = 0F,
    userLevel: Int = 1,
    LevelExp: Float = 100F,
    completeReward: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    // 인장 애니메이션 관련 변수
    var isBadgeSlide by remember { mutableStateOf(true) }
    var isBadgeTransparent by remember { mutableStateOf(true) }
    var isMessageTransparent by remember { mutableStateOf(false) }
    var isFanFareTransparent by remember { mutableStateOf(false) }

    // 경험치 애니메이션 관련 변수
    var initalExpRate by remember { mutableStateOf(userExp / LevelExp) }
    var targetExpRate by remember { mutableStateOf(userExp / LevelExp) }
    var currentUserLevel by remember { mutableStateOf(userLevel) }
    var showingUserLevel by remember { mutableStateOf(userLevel) }
    var nextUserLevel by remember { mutableStateOf(userLevel + 1) }
    var isLevelUp by remember { mutableStateOf(false) }

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
        animationSpec = tween(durationMillis = 500),
    )

    val fanfareAlpha by animateFloatAsState(
        targetValue = if (isFanFareTransparent) 1.0f else 0.0f,
        animationSpec = tween(durationMillis = 1000),
    )

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.celebration))
    val animationState = rememberLottieAnimatable()

    LaunchedEffect(Unit) {
        if (userExp + userGameScore >= LevelExp) {
            targetExpRate = 1F
            isLevelUp = true
        } else {
            targetExpRate = (userExp + userGameScore) / LevelExp
        }

        if(isLevelUp) {
            delay(2000L)
            isBadgeTransparent = false
            isBadgeSlide = false
            currentUserLevel += 1
            nextUserLevel += 1
            targetExpRate = 0F
            initalExpRate = 0F
            delay(1000L)
            isMessageTransparent = true
            isBadgeTransparent = true
            isBadgeSlide = true
            isFanFareTransparent = true
            showingUserLevel += 1
            val newRate = (userExp + userGameScore - LevelExp) / (LevelExp + 200)
            targetExpRate = newRate
            initalExpRate = newRate

            animationState.animate(
                composition = composition,
                iterations = 1,
            )
        }
    }

    Box(
        modifier =
        modifier
            .fillMaxSize()
            .padding(vertical = 20.dp),
    ) {
        Column(
            modifier =
            modifier
                .fillMaxSize()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "승급 !",
                style = Typography.displayLarge,
                fontSize = 40.sp,
                modifier =
                modifier
                    .padding(top = 100.dp)
                    .graphicsLayer(alpha = messageAlpha),
            )
            Box(
                modifier =
                    Modifier
                        .size(400.dp),
                contentAlignment = Alignment.Center,
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = animationState.progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer(alpha = fanfareAlpha),
                )
                BigBadge(
                    level = showingUserLevel,
                    modifier =
                    Modifier
                        .padding(top = badgePaddingSize)
                        .graphicsLayer(alpha = badgeAlpha),
                )
            }
            Spacer(modifier = Modifier.size(80.dp))
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
            modifier
                .padding(horizontal = 40.dp, vertical = 80.dp)
                .align(Alignment.BottomCenter),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "$currentUserLevel",
                    style = Typography.displayLarge,
                    fontSize = 40.sp,
                )
                Text("EXP +${userGameScore.toInt()}")
                Text(
                    text = "$nextUserLevel",
                    style = Typography.displayLarge,
                    fontSize = 40.sp,
                )
            }
            AnimatedGaugeBar(
                modifier = modifier.padding(top = 10.dp),
                initialValue = initalExpRate,
                targetValue = targetExpRate,
            )
        }
        LongBlackButton(
            onClick = { completeReward() },
            text = stringResource(id = R.string.game_confirm),
            modifier = modifier.align(Alignment.BottomCenter),
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Game01RewardScreenPreview() {
    MallangTheme {
        Game01RewardScreen(
            userExp = 100F,
            userGameScore = 135F,
            userLevel = 10,
            LevelExp = 200F,
            completeReward = {},
        )
    }
}
