package com.chill.mallang.ui.feature.game.game01.Dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.chill.mallang.R
import com.chill.mallang.data.model.entity.Game01QuizData
import com.chill.mallang.ui.component.TextBoxWithTitle
import com.chill.mallang.ui.feature.home.ImageButton
import com.chill.mallang.ui.theme.Gray4
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.QuokkaRealBrown
import com.chill.mallang.ui.theme.SuperLightGray
import com.chill.mallang.ui.theme.Typography
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GameReviewDialog(
    index: Int,
    roundResults: List<RoundResult>,
    onDismiss: () -> Unit,
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxWidth(0.95f),
        properties = DialogProperties(usePlatformDefaultWidth = false),
        content = {
            Surface(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85f)
                    .padding(10.dp),
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(width = 2.dp, color = Gray6),
                color = Color.White,
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val pagerState =
                        rememberPagerState(
                            initialPage = index,
                            pageCount = { roundResults.size },
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
                        Spacer(modifier = Modifier.weight(1f))

                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "${currentPage + 1} / ${roundResults.size}",
                                style = Typography.displaySmall,
                                color = Gray4,
                            )
                        }

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

                    HorizontalPager(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                            .padding(top = 20.dp),
                        state = pagerState,
                        pageSpacing = pageSpacing,
                    ) { page ->
                        val reviewContent = roundResults[page]
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
                            GameReviewCardContent(
                                reviewContent = reviewContent,
                            )
                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.CenterEnd,
                            ) {
                                if (page < roundResults.size - 1) {
                                    ImageButton(icon = R.drawable.ic_next, label = "", onClick = {
                                        scope.launch {
                                            pagerState.animateScrollToPage(
                                                page + 1,
                                            )
                                        }
                                    })
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
fun GameReviewCardContent(
    modifier: Modifier = Modifier,
    reviewContent: RoundResult,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 10.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(
                text = stringResource(id = R.string.round_result_title_foramt, reviewContent.round),
                style = Typography.displaySmall,
                fontSize = 40.sp,
                modifier = Modifier.padding(vertical = 10.dp),
            )
            Spacer(modifier = Modifier.width(30.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier =
                modifier
                    .size(80.dp)
            ) {
                CircularProgressIndicator(
                    progress = { reviewContent.userScore / 100F },
                    modifier = modifier.size(70.dp),
                    strokeWidth = 4.dp,
                    strokeCap = StrokeCap.Round,
                    color = QuokkaRealBrown,
                    trackColor = SuperLightGray
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.align(Alignment.Center),
                ) {
                    Text(
                        text = "${reviewContent.userScore}점",
                        color = QuokkaRealBrown,
                        style = Typography.displayLarge,
                        fontSize = 30.sp,
                    )
                    Text(
                        text = "/${100}점",
                        style = Typography.displayLarge,
                        fontSize = 16.sp,
                    )
                }
            }
        }
        LazyColumn {
            item {
                TextBoxWithTitle(
                    title = "문제",
                    content = reviewContent.quizDataSet.question,
                )
            }
            item {
                TextBoxWithTitle(
                    title = "사용자 답안",
                    content = reviewContent.userAnswer,
                )
            }
            item {
                TextBoxWithTitle(
                    title = "AI 답안",
                    content = reviewContent.quizDataSet.answer,
                )
            }
        }
    }
}

data class RoundResult(
    val round: Int,
    val quizDataSet: Game01QuizData,
    val userAnswer: String,
    val userScore: Int,
)

@Preview(showBackground = true)
@Composable
fun GameReviewDialogPreview() {
    MallangTheme {
        GameReviewDialog(
            index = 3,
            roundResults =
                listOf(
                    RoundResult(
                        round = 1,
                        quizDataSet =
                            Game01QuizData(
                                id = 1,
                                question = "이것은 질문입니다1.",
                                answer = "이것은 답변입니다.",
                                difficulty = 1,
                            ),
                        userAnswer = "이것은 사용자 답변입니다.",
                        userScore = 100,
                    ),
                    RoundResult(
                        round = 2,
                        quizDataSet =
                            Game01QuizData(
                                id = 1,
                                question = "이것은 질문입니다2.",
                                answer = "이것은 답변입니다.",
                                difficulty = 1,
                            ),
                        userAnswer = "이것은 사용자 답변입니다.",
                        userScore = 100,
                    ),
                    RoundResult(
                        round = 3,
                        quizDataSet =
                            Game01QuizData(
                                id = 1,
                                question = "이것은 질문입니다3.",
                                answer = "이것은 답변입니다.",
                                difficulty = 1,
                            ),
                        userAnswer = "이것은 사용자 답변입니다.",
                        userScore = 100,
                    ),
                ),
            onDismiss = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameReviewCardContentPreview() {
    MallangTheme {
        GameReviewCardContent(
            reviewContent = RoundResult(
                round = 3,
                quizDataSet =
                Game01QuizData(
                    id = 1,
                    question = "이것은 질문입니다3.",
                    answer = "이것은 답변입니다.",
                    difficulty = 1,
                ),
                userAnswer = "이것은 사용자 답변입니다.",
                userScore = 100,
            ),
        )
    }
}
