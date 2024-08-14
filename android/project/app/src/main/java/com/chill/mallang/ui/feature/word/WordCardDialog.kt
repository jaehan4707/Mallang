package com.chill.mallang.ui.feature.word

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.chill.mallang.R
import com.chill.mallang.ui.component.PosBox
import com.chill.mallang.ui.feature.home.ImageButton
import com.chill.mallang.ui.theme.Gray3
import com.chill.mallang.ui.theme.Gray4
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.Typography
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WordCardDialog(
    index: Int,
    wordCards: List<CorrectWord>,
    onDismiss: () -> Unit,
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxWidth(0.95f).wrapContentHeight().testTag("word_card_dialog"),
        properties = DialogProperties(usePlatformDefaultWidth = false),
        content = {
            Surface(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .heightIn(min = 250.dp)
                        .padding(20.dp),
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(width = 2.dp, color = Gray6),
                color = Color.White,
            ) {
                Box(modifier = Modifier.padding(bottom = 20.dp)) {
                    Column {
                        val pagerState =
                            rememberPagerState(
                                initialPage = index,
                                pageCount = { wordCards.size },
                            )
                        val pageSpacing = 15.dp
                        val scope = rememberCoroutineScope()

                        val currentPage by remember { derivedStateOf { pagerState.currentPage } }

                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            // 왼쪽 빈 공간
                            Spacer(modifier = Modifier.weight(1f))

                            // 중앙 텍스트
                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = "${currentPage + 1} / ${wordCards.size}",
                                    style = Typography.displaySmall,
                                    color = Gray4,
                                )
                            }

                            // 오른쪽 닫기 아이콘
                            Box(
                                modifier =
                                    Modifier
                                        .padding(10.dp)
                                        .size(15.dp)
                                        .weight(1f),
                                contentAlignment = Alignment.CenterEnd,
                            ) {
                                ImageButton(
                                    icon = R.drawable.ic_close,
                                    label = "",
                                    onClick = onDismiss,
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        HorizontalPager(
                            modifier =
                                Modifier
                                    .fillMaxWidth(),
                            state = pagerState,
                            pageSpacing = pageSpacing,
                        ) { page ->
                            val word = wordCards[page]
                            Row(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 5.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Box(
                                    modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.CenterStart,
                                ) {
                                    if (page > 0) {
                                        ImageButton(
                                            icon = R.drawable.ic_prev,
                                            label = "",
                                            onClick = {
                                                scope.launch {
                                                    pagerState.animateScrollToPage(page - 1)
                                                }
                                            },
                                        )
                                    }
                                }
                                WordCardContent(
                                    modifier = Modifier.weight(8f),
                                    card = word,
                                )
                                Box(
                                    modifier = Modifier.weight(1f),
                                    contentAlignment = Alignment.CenterEnd,
                                ) {
                                    if (page < wordCards.size - 1) {
                                        ImageButton(
                                            icon = R.drawable.ic_next,
                                            label = "",
                                            onClick = {
                                                scope.launch {
                                                    pagerState.animateScrollToPage(
                                                        page + 1,
                                                    )
                                                }
                                            },
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
    )
}

@Composable
fun WordCardContent(
    modifier: Modifier = Modifier,
    card: CorrectWord,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.testTag("word_card_dialog_word"),
                    text = card.word,
                    style = Typography.headlineLarge,
                    color = Gray6,
                )
                Spacer(modifier = Modifier.width(10.dp))
                PosBox(pos = card.pos)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.testTag("word_card_dialog_meaning"),
                text = card.meaning,
                style = Typography.headlineSmall,
                color = Gray6,
            )
        }

        Spacer(modifier = Modifier.height(25.dp))
        Spacer(
            modifier =
                Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Gray3),
        )
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            modifier =
                Modifier
                    .fillMaxWidth().testTag("word_card_dialog_example"),
            text = card.example,
            style = Typography.headlineSmall,
            textAlign = TextAlign.Center,
            color = Gray6,
        )
    }
}

@Preview
@Composable
fun Preview() {
    WordCardDialog(
        index = 1,
        wordCards =
            arrayListOf(
                CorrectWord(
                    word = "단어",
                    pos = "명사",
                    meaning = "여기는 단어의 뜻을 나타내는 공간12312312312311231232131231231",
                    example = "여기는 단어를 사용한 예시를 나타내는 공간12312312312312312312312312312312312312123123",
                ),
            ),
    ) {}
}
