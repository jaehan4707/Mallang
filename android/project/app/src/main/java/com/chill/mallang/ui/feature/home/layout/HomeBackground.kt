package com.chill.mallang.ui.feature.home.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chill.mallang.R
import com.chill.mallang.ui.component.experiencebar.ExperienceBar
import com.chill.mallang.ui.component.experiencebar.ExperienceState
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun HomeBackground(
    modifier: Modifier = Modifier,
    nickName: String,
    factionId: Long,
    experienceState: ExperienceState,
) {
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = Color.Transparent),
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.img_home_background),
            contentScale = ContentScale.FillWidth,
            contentDescription = null,
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ExperienceBar(
                modifier = Modifier.padding(16.dp),
                experienceState = experienceState,
            )
            Spacer(modifier = Modifier.weight(1f))
            HomeCharacter(
                modifier = Modifier.offset(y = (-100).dp),
                nickName = nickName,
                factionId = factionId,
                experienceState = experienceState,
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview
@Composable
fun HomeBackgroundPreview() {
    MallangTheme {
        HomeBackground(
            nickName = "짜이한",
            factionId = 1,
            experienceState = ExperienceState.Static(0.5f, 1),
        )
    }
}
