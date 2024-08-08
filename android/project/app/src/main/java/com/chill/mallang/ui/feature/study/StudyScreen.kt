package com.chill.mallang.ui.feature.study

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.chill.mallang.R
import com.chill.mallang.ui.component.BackConfirmHandler
import com.chill.mallang.ui.component.CustomSnackBar
import com.chill.mallang.ui.component.LoadingDialog
import com.chill.mallang.ui.feature.topbar.TopbarHandler
import com.chill.mallang.ui.theme.Gray3
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Sub1
import com.chill.mallang.ui.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun StudyScreen(
    modifier: Modifier = Modifier,
    studyViewModel: StudyViewModel = hiltViewModel(),
    studyId: Int = -1,
    popUpBackStack: () -> Unit = {},
    navigateToQuizResult: (Int) -> Unit = {},
) {
    val studyState by studyViewModel.studyState.collectAsStateWithLifecycle()

    studyViewModel.loadQuizData(studyId)

    // TopBar
    val (navController, setNavController) = remember { mutableStateOf<NavController?>(null) }
    val (isBackPressed, setBackPressed) = remember { mutableStateOf(false) }

    // context
    val context = LocalContext.current

    BackConfirmHandler(
        isBackPressed = isBackPressed,
        onConfirmMessage = stringResource(R.string.study_dialog_confirm_message),
        onConfirm = {
            setBackPressed(false)
            popUpBackStack()
        },
        onDismissMessage = stringResource(R.string.study_dialog_dismiss_message),
        onDismiss = {
            setBackPressed(false)
        },
        title = stringResource(R.string.study_dialog_title),
        content = stringResource(R.string.study_dialog_content),
    )
    BackHandler(onBack = { setBackPressed(true) })

    TopbarHandler(
        isVisible = true,
        title = context.getString(R.string.study_quiz_title),
        onBack = { nav ->
            setBackPressed(true)
            setNavController(nav)
        },
    )

    when (studyState) {
        is StudyState.Success -> {
            StudyScreenContent(
                modifier = modifier,
                context = context,
                studyViewModel = studyViewModel,
                studyState = studyState as StudyState.Success,
                navigateToQuizResult = navigateToQuizResult,
            )
        }

        is StudyState.Error -> {
            // 에러났을 때 처리
        }

        StudyState.Loading -> LoadingDialog()
    }
}

@Composable
fun StudyScreenContent(
    modifier: Modifier = Modifier,
    context: Context,
    studyViewModel: StudyViewModel,
    studyState: StudyState.Success,
    navigateToQuizResult: (Int) -> Unit = {},
) {
    // SnackBarHostState 생성
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                snackbar = { snackBarData ->
                    CustomSnackBar(
                        snackBarData = snackBarData,
                        backgroundColor = Sub1,
                        textColor = Color.White,
                    )
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier =
                modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                    .padding(innerPadding),
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.height(15.dp))
                QuizBox(
                    quizTitle = studyState.quizTitle,
                    quizScript = studyState.quizScript,
                )
                Spacer(modifier = Modifier.weight(1f))
                AnswerList(
                    viewModel = studyViewModel,
                    state = studyState,
                    fraction = 0.15f,
                    onAnswerSelected = { selectedIndex ->
                        studyViewModel.selectAnswer(selectedIndex)
                    },
                )
            }
            Button(
                onClick = {
                    if (studyViewModel.selectedAnswer == -1) {
                        // 스낵바 띄우기
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                message = context.getString(R.string.unselect_snackbar_message),
                                duration = SnackbarDuration.Short,
                            )
                        }
                    } else {
                        studyViewModel.submitQuiz() // 퀴즈 제출 및 채점
                        navigateToQuizResult(studyViewModel.selectedAnswer)
                    }
                },
                modifier =
                    Modifier
                        .align(Alignment.BottomEnd)
                        .offset(y = (-30).dp) // 버튼을 20dp 위로 올림
                        .width(180.dp)
                        .height(80.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = Gray6,
                    ),
                shape = RoundedCornerShape(20.dp, 0.dp, 0.dp, 20.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = context.getString(R.string.submit_study),
                        style = Typography.headlineLarge,
                        textAlign = TextAlign.End,
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_next),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Composable
fun QuizBox(
    quizTitle: String,
    quizScript: String,
) {
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
                    text = quizTitle,
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
                text = quizScript,
                style = Typography.headlineSmall,
            )
        }
    }
}

@Composable
fun AnswerList(
    state: StudyState.Success,
    viewModel: StudyViewModel,
    fraction: Float,
    onAnswerSelected: (Int) -> Unit = { },
) {
    val size = state.wordList.size

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier =
            Modifier
                .padding(12.dp)
                .fillMaxSize(),
    ) {
        items(size) { index ->
            AnswerListItem(
                modifier = Modifier.fillParentMaxHeight(fraction),
                index = index,
                viewModel = viewModel,
                state = state,
                onItemClick = { selectedIndex ->
                    onAnswerSelected(selectedIndex)
                },
            )
        }
    }
}

@Composable
fun AnswerListItem(
    modifier: Modifier = Modifier,
    index: Int,
    viewModel: StudyViewModel,
    state: StudyState.Success,
    onItemClick: (Int) -> Unit,
) {
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
                        .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                        .border(2.dp, color = Gray6, shape = RoundedCornerShape(8.dp))
                        .clickable { onItemClick(index) },
            ) {
                Box(
                    modifier =
                        Modifier
                            .background(
                                color = Gray6,
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
                    text = state.wordList[index],
                    modifier = Modifier.weight(1f),
                    style = Typography.headlineLarge,
                )
                if (viewModel.selectedAnswer == index + 1) {
                    Icon(
                        modifier = Modifier.padding(10.dp),
                        painter = painterResource(id = R.drawable.ic_check),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun QuizPreview() {
    MallangTheme {
        StudyScreen()
    }
}
