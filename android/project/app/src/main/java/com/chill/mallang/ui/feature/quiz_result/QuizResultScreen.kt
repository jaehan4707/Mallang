package com.chill.mallang.ui.feature.quiz_result

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chill.mallang.R
import com.chill.mallang.ui.component.BackConfirmHandler
import com.chill.mallang.ui.feature.quiz.AnswerList
import com.chill.mallang.ui.theme.Gray3
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.Green1
import com.chill.mallang.ui.theme.Green2
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Sub1
import com.chill.mallang.ui.theme.Sub2
import com.chill.mallang.ui.theme.Typography

@Composable
fun QuizResultScreen(
    modifier: Modifier = Modifier,
    popUpBackStack: () -> Unit = {},
) {
    val quizResultViewModel: QuizResultViewModel = hiltViewModel()
    val state = quizResultViewModel.state

    var expandedItem by remember { mutableIntStateOf(-1) }

    val isBackPressed = remember { mutableStateOf(false) }
    BackConfirmHandler(
        isBackPressed = isBackPressed.value,
        onConfirm = {
            isBackPressed.value = false
            popUpBackStack()
        },
        onDismiss = {
            isBackPressed.value = false
        },
    )
    BackHandler(onBack = { isBackPressed.value = true })

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = Color.White),
    ) {
        Column(
            modifier =
                modifier
                    .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val title = if (state.userAnswer == state.systemAnswer) "정답!" else "오답!"
            val titleColor = if (title == "정답!") Green1 else Sub1

            Text(
                modifier = Modifier.padding(vertical = 10.dp),
                text = title,
                style = Typography.headlineLarge,
                fontSize = 40.sp,
                color = titleColor,
            )
            QuizBoxWithUnderline(
                systemMessage = state.quizTitle,
                quizScript = state.quizScript,
                underline = state.wordList[state.systemAnswer - 1].first,
            )
            AnswerList(
                state = state,
                fraction = 0.15f,
                expandedItem = expandedItem,
                onAnswerSelected = { selectedIndex ->
                    expandedItem = if (expandedItem == selectedIndex + 1) -1 else selectedIndex + 1
                },
            )
        }
    }
}

@Composable
fun AnswerResultListItem(
    modifier: Modifier = Modifier,
    index: Int,
    state: QuizResultState,
    expandedItem: Int,
    onItemClick: (Int) -> Unit,
) {
    val isUserAnswer = state.userAnswer == index + 1
    val isSystemAnswer = state.systemAnswer == index + 1

    // 배경색
    val backgroundColor =
        when {
            isUserAnswer && !isSystemAnswer -> Sub2
            isSystemAnswer -> Green2
            else -> Color.White
        }

    // 번호, 테두리 색상
    val borderColor =
        when {
            isUserAnswer && !isSystemAnswer -> Sub1
            isSystemAnswer -> Green1
            else -> Gray6
        }

    // 뜻 확장 및 애니메이션 효과
    val expandTransition =
        updateTransition(targetState = expandedItem, label = "expandTransition")
    val expandedHeight by expandTransition.animateDp(
        label = "expandedHeight",
        transitionSpec = { tween(durationMillis = 200) },
    ) { expanded ->
        if (expanded == index + 1) 40.dp else 0.dp
    }

    val alpha by expandTransition.animateFloat(
        label = "alpha",
        transitionSpec = { tween(durationMillis = 200) },
    ) { expanded ->
        if (expanded == index + 1) 1f else 0f
    }

    Column {
        Box(
            modifier =
                modifier
                    .shadow(
                        elevation = 5.dp,
                        shape = RoundedCornerShape(8.dp),
                    ),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier =
                    modifier
                        .fillMaxWidth()
                        .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
                        .border(2.dp, color = borderColor, shape = RoundedCornerShape(8.dp))
                        .clickable { onItemClick(index) },
            ) {
                Box(
                    modifier =
                        Modifier
                            .background(
                                color = borderColor,
                                shape = RoundedCornerShape(8.dp, 0.dp, 0.dp, 8.dp),
                            ).fillMaxHeight()
                            .width(50.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "${index + 1}",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        style = Typography.headlineLarge,
                    )
                }
                Spacer(modifier = Modifier.width(30.dp))
                Text(
                    text = state.wordList[index].first,
                    modifier = Modifier.weight(1f),
                    style = Typography.headlineLarge,
                )
                when (expandedItem == index + 1) {
                    false -> {
                        Icon(
                            modifier = Modifier.padding(10.dp),
                            painter = painterResource(id = R.drawable.ic_down),
                            contentDescription = null,
                        )
                    }

                    true -> {
                        Icon(
                            modifier = Modifier.padding(10.dp),
                            painter = painterResource(id = R.drawable.ic_up),
                            contentDescription = null,
                        )
                    }
                }
            }
        }
        if (expandedItem == index + 1) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(expandedHeight)
                        .background(color = Color.Unspecified),
            ) {
                Text(
                    text = state.wordList[index].second,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                            .alpha(alpha),
                    style = Typography.displayMedium,
                    textAlign = TextAlign.Left,
                )
            }
        }
    }
}

@Composable
fun QuizBoxWithUnderline(
    systemMessage: String,
    quizScript: String,
    underline: String,
) {
    Log.d("nakyung", underline)
    Box(
        modifier =
            Modifier
                .padding(12.dp)
                .border(width = 2.dp, color = Gray6, shape = RoundedCornerShape(10.dp))
                .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 30.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Q.",
                    style = Typography.headlineLarge,
                )
                Box(modifier = Modifier.width(10.dp))
                Text(
                    text = systemMessage,
                    style = Typography.headlineMedium,
                )
            }
            Box(modifier = Modifier.height(15.dp))
            Spacer(
                modifier =
                    Modifier
                        .height(2.dp)
                        .fillMaxWidth()
                        .background(Gray3),
            )
            Box(modifier = Modifier.height(15.dp))
            Text(
                text =
                    buildAnnotatedString {
                        val startIndex = quizScript.indexOf(underline)
                        if (startIndex != -1) {
                            append(quizScript.substring(0, startIndex))
                            withStyle(
                                style =
                                    SpanStyle(
                                        textDecoration = TextDecoration.Underline,
                                    ),
                            ) {
                                append(underline)
                            }
                            append(quizScript.substring(startIndex + underline.length))
                        } else {
                            append(quizScript)
                        }
                    },
                style = Typography.headlineSmall,
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ResultPreview() {
    MallangTheme {
        QuizResultScreen()
    }
}
