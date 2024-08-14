package com.chill.mallang.ui.feature.home.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chill.mallang.R
import com.chill.mallang.ui.component.experiencebar.ExperienceState
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography

@Composable
fun HomeCharacter(
    modifier: Modifier = Modifier,
    nickName: String,
    factionId: Long,
    experienceState: ExperienceState,
) {
    val resId by remember {
        derivedStateOf { if (factionId == 1L) R.drawable.img_mal_default_character else R.drawable.img_lang_default_character }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(200.dp).testTag("user_img"),
            painter = painterResource(id = resId),
            contentDescription = "$resId",
        )
        Text(
            modifier = Modifier.testTag("home_nickname"),
            text = nickName,
            style = Typography.headlineLarge,
        )
    }
}

@Preview
@Composable
fun HomeCharacterPreview() {
    MallangTheme {
        HomeCharacter(
            nickName = "짜이한",
            factionId = 1,
            experienceState = ExperienceState.Static(0.5f, 1),
        )
    }
}
