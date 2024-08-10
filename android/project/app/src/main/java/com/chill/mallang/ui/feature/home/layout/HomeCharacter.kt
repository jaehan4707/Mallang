package com.chill.mallang.ui.feature.home.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.chill.mallang.ui.component.experiencebar.ExperienceState
import com.chill.mallang.ui.component.experiencebar.LevelCharacter
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography

@Composable
fun HomeCharacter(
    modifier: Modifier = Modifier,
    nickName: String,
    experienceState: ExperienceState,
) {
    val resId by remember {
        derivedStateOf { LevelCharacter.getResourceOfLevel(experienceState.level) }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = null,
        )
        Text(text = nickName, style = Typography.headlineLarge)
    }
}

@Preview
@Composable
fun HomeCharacterPreview() {
    MallangTheme {
        HomeCharacter(nickName = "짜이한", experienceState = ExperienceState.Static(0.5f, 1))
    }
}
