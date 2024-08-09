package com.chill.mallang.ui.feature.summary

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chill.mallang.R
import com.chill.mallang.data.model.entity.User
import com.chill.mallang.ui.component.BoldColoredText
import com.chill.mallang.ui.component.LoadingDialog
import com.chill.mallang.ui.component.LongBlackButton
import com.chill.mallang.ui.component.PercentageBar
import com.chill.mallang.ui.feature.home.ImageButton
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.SkyBlue
import com.chill.mallang.ui.theme.Sub1
import com.chill.mallang.ui.theme.Sub3
import com.chill.mallang.ui.theme.Sub4
import com.chill.mallang.ui.theme.Typography
import com.chill.mallang.ui.util.noRippleClickable

val SampleData =
    listOf(
        User(
            nickName = "닉네임111111111111111111111111111111111111111111111111",
            factionId = 1L,
        ) to listOf(10, 20),
        User(nickName = "이나갱", factionId = 1L) to listOf(20, 30),
        User(nickName = "코리안캉", factionId = 1L) to listOf(30, 40),
        User(nickName = "성수킴", factionId = 1L) to listOf(1, 9),
        User(nickName = "정인킴", factionId = 2L) to listOf(20, 3),
        User(nickName = "혜림나", factionId = 2L) to listOf(32, 13),
        User(nickName = "서원변", factionId = 2L) to listOf(21, 10),
        User(nickName = "서원변", factionId = 2L) to listOf(21, 10),
        User(nickName = "서원변", factionId = 2L) to listOf(21, 10),
        User(nickName = "서원변", factionId = 2L) to listOf(21, 10),
        User(nickName = "서원변", factionId = 2L) to listOf(21, 10),
    )

@Composable
fun SummaryScreen(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit = {},
    viewModel: SummaryViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    when (uiState.value) {
        SummaryUiState.Loading -> {
            LoadingDialog(
                lottieRes = R.raw.loading_summary,
                loadingMessage = "결산 로딩중... ",
            )
        }

        SummaryUiState.Success -> {
            SummaryContent(modifier = modifier, navigateToHome)
        }
    }
}

@Composable
fun SummaryContent(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit = {},
) {
    Box(modifier = modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement =
                Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier =
                    Modifier
                        .weight(0.07f)
                        .wrapContentWidth(),
                text = stringResource(R.string.summary_title),
                style = Typography.headlineLarge,
            )
            // TODO 결산 화면 용 이미지 리소스 하나 -> 말 vs 랑 대치하는 이미지
            Image(
                modifier = Modifier.weight(0.2f),
                painter = painterResource(id = R.mipmap.yellow),
                contentDescription = "",
            )
            PercentageBar(
                modifier = Modifier.wrapContentWidth(),
                leftPercentage = 10,
                rightPercentage = 90,
                leftLabel = stringResource(id = R.string.team_mal),
                rightLabel = stringResource(id = R.string.team_rang),
                leftColor = Sub1,
                rightColor = SkyBlue,
            )

            LazyVerticalGrid(
                modifier = Modifier.weight(1f),
                columns = GridCells.Fixed(2),
                state = rememberLazyGridState(),
                contentPadding = PaddingValues(5.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(SampleData.size, span = { index ->
                    GridItemSpan(1)
                }) { index ->
                    val data = SampleData[index]
                    SummaryItem(
                        areaName = "점령지112312312312312312312312",
                        score = data.second,
                        topUser = data.first,
                    )
                }
            }

            LongBlackButton(modifier = Modifier.weight(0.1f), onClick = navigateToHome, text = "확인")
        }
    }
}

@Composable
fun SummaryItem(
    modifier: Modifier = Modifier,
    areaName: String = "111111111111111111111111111111111111111111111111",
    score: List<Int> = listOf(10, 20),
    topUser: User = User(nickName = "짜이한", factionId = 1L),
    onClick: () -> Unit = {},
) {
    val color =
        if (topUser.factionId == 1L) {
            Sub3
        } else {
            Sub4
        }
    Card(
        modifier =
            modifier
                .size(180.dp)
                .noRippleClickable {
                    onClick()
                },
        shape = RoundedCornerShape(15.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .background(color)
                    .padding(7.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.weight(0.15f),
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = "",
                )
                Text(
                    modifier = Modifier.weight(0.6f),
                    text = areaName,
                    style = Typography.displayLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.weight(0.1f))
                BoldColoredText(
                    modifier = Modifier.weight(0.1f),
                    text = score[0].toString(),
                    textColor = Sub1,
                )
                BoldColoredText(
                    modifier = Modifier.weight(0.1f),
                    text = " : ",
                    textColor = Color.Black,
                )
                BoldColoredText(
                    modifier = Modifier.weight(0.1f),
                    text = score[1].toString(),
                    textColor = SkyBlue,
                )
            }

            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.mipmap.yellow),
                contentDescription = "",
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = topUser.nickName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                style = Typography.headlineSmall,
            )
            ImageButton(
                modifier = Modifier.align(Alignment.End),
                icon = R.drawable.ic_search,
                label = "",
                onClick = {},
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun SummaryPreview() {
    MallangTheme {
        SummaryContent()
    }
}
