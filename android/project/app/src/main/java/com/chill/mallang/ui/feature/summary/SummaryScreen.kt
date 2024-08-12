package com.chill.mallang.ui.feature.summary

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.chill.mallang.data.model.entity.Summary
import com.chill.mallang.ui.component.BackConfirmHandler
import com.chill.mallang.ui.component.BoldColoredText
import com.chill.mallang.ui.component.LoadingDialog
import com.chill.mallang.ui.component.LongBlackButton
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.SkyBlue
import com.chill.mallang.ui.theme.Sub1
import com.chill.mallang.ui.theme.Sub3
import com.chill.mallang.ui.theme.Sub4
import com.chill.mallang.ui.theme.Typography
import com.chill.mallang.ui.util.noRippleClickable
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun SummaryScreen(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit = {},
    viewModel: SummaryViewModel = hiltViewModel(),
    popUpBackStack: () -> Unit = {},
    onShowErrorSnackBar: (String) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isBackPressed = remember { mutableStateOf(false) }
    BackConfirmHandler(
        isBackPressed = isBackPressed.value,
        onConfirmMessage = stringResource(id = R.string.positive_button_message),
        onConfirm = {
            isBackPressed.value = false
            popUpBackStack()
        },
        onDismissMessage = stringResource(id = R.string.nagative_button_message),
        onDismiss = {
            isBackPressed.value = false
        },
        title = stringResource(R.string.app_exit_message),
    )
    BackHandler(onBack = {
        isBackPressed.value = true
    })

    HandleSummaryUi(
        uiState = uiState,
        navigateToHome = navigateToHome,
        modifier = modifier,
        onShowErrorSnackBar = onShowErrorSnackBar,
    )
}

@Composable
fun HandleSummaryUi(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit = {},
    uiState: SummaryUiState,
    onShowErrorSnackBar: (String) -> Unit,
) {
    LaunchedEffect(uiState) {
        when (uiState) {
            is SummaryUiState.Error -> {
                onShowErrorSnackBar(uiState.errorMessage)
            }

            else -> {}
        }
    }
    when (uiState) {
        SummaryUiState.Loading -> {
            LoadingDialog(
                lottieRes = R.raw.loading_summary,
                loadingMessage = "결산 로딩중... ",
            )
        }

        is SummaryUiState.Success -> {
            SummaryContent(
                modifier = modifier,
                navigateToHome,
                summaryRecords = uiState.summaryRecords,
            )
        }

        else -> {}
    }
}

@Composable
fun SummaryContent(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit = {},
    summaryRecords: PersistentList<Summary> = persistentListOf(),
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
                        .wrapContentWidth(),
                text = stringResource(R.string.summary_title),
                style = Typography.headlineLarge,
            )
            Box(modifier = modifier.wrapContentWidth(), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.img_summary_background),
                    contentDescription = "",
                )
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                    ) {
                        Image(
                            modifier = Modifier.size(150.dp),
                            alignment = Alignment.CenterStart,
                            painter = painterResource(id = R.drawable.img_lang_fighting),
                            contentDescription = "",
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Image(
                            modifier = Modifier.size(150.dp),
                            painter = painterResource(id = R.drawable.img_mal_fighting),
                            contentDescription = "",
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text =
                                "🏰 " +
                                    summaryRecords
                                        .count { it.victoryFactionId == 2 }
                                        .toString(),
                            modifier = Modifier.weight(0.5f),
                            textAlign = TextAlign.Center,
                            style = Typography.headlineLarge,
                        )
                        Text(
                            text =
                                "\uD83C\uDFF0 " +
                                    summaryRecords
                                        .count { it.victoryFactionId == 1 }
                                        .toString(),
                            modifier = Modifier.weight(0.5f),
                            textAlign = TextAlign.Center,
                            style = Typography.headlineLarge,
                        )
                    }
                }
            }

            LazyVerticalGrid(
                modifier = Modifier.weight(1f),
                columns = GridCells.Fixed(2),
                state = rememberLazyGridState(),
                contentPadding = PaddingValues(5.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(summaryRecords.size, span = { index ->
                    GridItemSpan(1)
                }) { index ->
                    val data = summaryRecords[index]
                    SummaryItem(
                        areaName = data.areaName,
                        score = listOf(data.malScore.toInt(), data.langScore.toInt()),
                        victoryFactionId = data.victoryFactionId,
                        userNickName = data.topUserNickName,
                    )
                }
            }

            LongBlackButton(modifier = Modifier.weight(0.1f), onClick = navigateToHome, text = "확인")
        }
    }
}

@Composable
fun SummaryItems() {
}

@Composable
fun SummaryItem(
    modifier: Modifier = Modifier,
    areaName: String = "점령지1",
    score: List<Int> = listOf(10, 20),
    userNickName: String = "짜이한",
    victoryFactionId: Int = 1,
    onClick: () -> Unit = {},
) {
    val color =
        if (victoryFactionId == 1) {
            Sub3
        } else {
            Sub4
        }
    val malImage =
        if (victoryFactionId == 1) {
            R.drawable.img_mal_default_character
        } else {
            R.drawable.img_lang_default_character
        }
    Card(
        modifier =
            modifier
                .wrapContentSize()
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
                painter = painterResource(id = malImage),
                contentDescription = "",
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "👑 $userNickName",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                style = Typography.headlineSmall,
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun SummaryPreview() {
    MallangTheme {
        SummaryContent(
            summaryRecords =
                persistentListOf(
                    Summary(
                        areaName = "점령지1",
                        malScore = 101.0,
                        langScore = 100.0,
                        topUserNickName = "이나갱",
                        victoryFactionId = 1,
                    ),
                    Summary(
                        areaName = "점령지2",
                        malScore = 100.0,
                        topUserNickName = "짜이한",
                        langScore = 101.0,
                        victoryFactionId = 2,
                    ),
                ),
        )
    }
}
