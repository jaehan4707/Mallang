package com.chill.mallang.ui.feature.home.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chill.mallang.ui.component.experiencebar.ExperienceBar
import com.chill.mallang.ui.component.experiencebar.ExperienceState
import com.chill.mallang.ui.theme.Green03
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun HomeBackground(
    modifier: Modifier = Modifier,
    nickName: String,
    experienceState: ExperienceState,
) {
    Box(modifier = modifier
        .fillMaxSize()
        .background(color = Green03)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ExperienceBar(
                modifier = Modifier.padding(16.dp),
                experienceState = experienceState,
            )
            Spacer(modifier = Modifier.weight(1f))
            HomeCharacter(nickName = nickName, experienceState = experienceState)
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview
@Composable
fun HomeBackgroundPreview() {
    MallangTheme {
        HomeBackground(nickName = "짜이한", experienceState = ExperienceState.Static(0.5f, 1))
    }
}
