package com.chill.mallang.ui.feature.game_lobby

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.chill.mallang.R
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography
import kotlin.math.absoluteValue

@Composable
fun GameLobbyScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        GameLobby_MainBody(
            modifier = Modifier.weight(10F)
        )
        GameLobby_GameStartBody(
            modifier = Modifier.weight(2F)
        )
    }
}

@Composable
fun GameLobby_MainBody(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        GamePicker()
    }
}

@Composable
fun GameLobby_GameStartBody(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 50.dp),
            onClick = {},
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                Color.Black,
                Color.White
            )
        ) {
            Text("게임 시작!", style = Typography.displayLarge, fontSize = 20.sp)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GamePicker(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState {
        3
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        CustomViewPager(pagerState = pagerState)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomViewPager(pagerState: PagerState) {
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 45.dp, vertical = 30.dp),
        modifier = Modifier.fillMaxWidth(),
    ) { page ->
        Card(
            modifier = Modifier
                .graphicsLayer {
                    val pageOffset =
                        ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                    scaleX = lerp(
                        start = 0.8f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                    scaleY = lerp(
                        start = 0.8f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
                .fillMaxWidth(1f)
        ) {
            GamePoster()
        }
    }
}

@Composable
fun GamePoster(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(Color.DarkGray)
                .fillMaxSize()
                .weight(2F)
        ) {
            Text("Image")
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1F)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Game 01",
                style = Typography.displayLarge,
                fontSize = 40.sp,
                modifier = Modifier
                    .weight(1F)
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                textAlign = TextAlign.Center,
            )
            Text(
                text = "Game 01에 대한 설명을 여기에 넣읍시다.",
                modifier = Modifier
                    .weight(1F)
                    .fillMaxWidth(),
                style = Typography.displayLarge,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun GameLobbyScreenPreview() {
    MallangTheme {
        GameLobbyScreen()
    }
}
