package com.chill.mallang.ui.component.experiencebar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.Green04
import com.chill.mallang.ui.theme.Green1
import com.chill.mallang.ui.theme.Green2
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun ExperienceBar(
    modifier: Modifier = Modifier,
    experienceState: ExperienceState,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        when(experienceState){
            is ExperienceState.Animate -> AnimatedGaugeBar(
                modifier = Modifier.align(Alignment.CenterStart)
                    .padding(start = 40.dp),
                initialValue = experienceState.prevValue,
                targetValue = experienceState.currentValue
            )
            is ExperienceState.Static -> GaugeBar(
                modifier = Modifier.align(Alignment.CenterStart)
                    .padding(start = 40.dp),
                percentage = experienceState.value
            )
            else -> GaugeBar(
                modifier = Modifier.align(Alignment.CenterStart)
                    .padding(start = 40.dp),
                percentage = 0f
            )
        }
        Badge(
            modifier = Modifier.align(Alignment.CenterStart),
            level = experienceState.level,
        )
    }
}

@Composable
fun AnimatedGaugeBar(
    modifier: Modifier = Modifier,
    initialValue: Float,
    targetValue: Float,
    animationDuration: Int = 1000,
) {
    var currentPercentage by remember { mutableFloatStateOf(initialValue) } // Trigger animation when targetPercentage changes

    LaunchedEffect(targetValue) {
        currentPercentage = targetValue
    }

    val animatedPercentage by animateFloatAsState(
        targetValue = currentPercentage,
        animationSpec = tween(durationMillis = animationDuration),
    )

    GaugeBar(modifier = modifier, percentage = animatedPercentage)
}

@Composable
fun GaugeBar(
    modifier: Modifier = Modifier,
    percentage: Float,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(28.dp)
                .border(4.dp, Green1, RoundedCornerShape(50))
                .background(Green2, RoundedCornerShape(50)),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(percentage)
                    .background(Green04, RoundedCornerShape(50)),
        )
    }
}

@Composable
fun Badge(
    modifier: Modifier = Modifier,
    level: Int,
) {
    val badge by remember(level) {
        derivedStateOf {
            LevelCharacter.getResourceOfLevel(level)
        }
    }

    Surface(
        modifier = modifier.size(width = 60.dp, height = 64.dp),
        color = Color.White,
        contentColor = Gray6,
        shape =
            RoundedCornerShape(
                topStart = 8.dp,
                topEnd = 8.dp,
                bottomStart = 100.dp,
                bottomEnd = 100.dp,
            ),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Image(
                modifier =
                    Modifier
                        .padding(horizontal = 8.dp)
                        .size(48.dp),
                painter = painterResource(id = badge),
                contentDescription = null,
            )
            Text(level.toString(), modifier = Modifier.padding(bottom = 8.dp))
        }
    }
}

@Preview
@Composable
fun ExperienceBarPreview() {
    MallangTheme {
        ExperienceBar(experienceState = ExperienceState.Static(0.5f, 1))
    }
}

@Preview
@Composable
fun AnimatedGaugeBarPreview() {
    MallangTheme {
        AnimatedGaugeBar(initialValue = 0f, targetValue = 0.5f)
    }
}

@Preview
@Composable
fun GaugeBarPreview() {
    MallangTheme {
        GaugeBar(percentage = 0.5f)
    }
}

@Preview
@Composable
fun BadgePreview() {
    MallangTheme {
        Badge(level = 1)
    }
}
