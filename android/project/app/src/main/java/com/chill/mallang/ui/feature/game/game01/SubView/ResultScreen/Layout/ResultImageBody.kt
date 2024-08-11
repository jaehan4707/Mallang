package com.chill.mallang.ui.feature.game.game01.SubView.ResultScreen.Layout

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chill.mallang.R
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun ResultImageBody(
    isVictory: Boolean,
    teamId: Long,
    modifier: Modifier = Modifier,
) {
    var isExpanded by remember { mutableStateOf(false) }
    val size by animateDpAsState(
        targetValue = if (isExpanded) 250.dp else 150.dp,
        animationSpec = tween(durationMillis = 1500),
    )

    LaunchedEffect(Unit) {
        isExpanded = true
    }

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .size(size),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            modifier = modifier.size(250.dp),
            painter =
                if(teamId == 1L) {
                    if(isVictory){
                        painterResource(id = R.drawable.img_win_mal_team)
                    } else {
                        painterResource(id = R.drawable.img_lose_mal_team)
                    }
                } else {
                    if(isVictory){
                        painterResource(id = R.drawable.img_win_lang_team)
                    } else {
                        painterResource(id = R.drawable.img_lose_lang_team)
                    }
                },
            contentDescription = "",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RedWinResultImageBodyPreview() {
    MallangTheme {
        ResultImageBody(
            isVictory = true,
            teamId = 1L,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RedLoseResultImageBodyPreview() {
    MallangTheme {
        ResultImageBody(
            isVictory = false,
            teamId = 1L,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BlueWinResultImageBodyPreview() {
    MallangTheme {
        ResultImageBody(
            isVictory = true,
            teamId = 2L,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BlueLoseResultImageBodyPreview() {
    MallangTheme {
        ResultImageBody(
            isVictory = false,
            teamId = 2L,
        )
    }
}
