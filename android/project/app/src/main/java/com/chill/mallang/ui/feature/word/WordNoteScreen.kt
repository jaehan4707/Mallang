package com.chill.mallang.ui.feature.word

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.chill.mallang.R
import com.chill.mallang.ui.component.BackConfirmHandler
import com.chill.mallang.ui.component.LoadingDialog
import com.chill.mallang.ui.component.LongBlackButton
import com.chill.mallang.ui.component.NoteChangeButton
import com.chill.mallang.ui.component.PosBox
import com.chill.mallang.ui.feature.topbar.TopbarHandler
import com.chill.mallang.ui.theme.BackGround
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.Typography

@Composable
fun WordNoteScreen(
    modifier: Modifier = Modifier,
    wordViewModel: WordNoteViewModel = hiltViewModel(),
    popUpBackStack: () -> Unit = {},
    navigateToIncorrectWord: () -> Unit = {},
    navigateToStudy: (Long) -> Unit = {},
) {
    val wordNoteState by wordViewModel.wordNoteState.collectAsStateWithLifecycle()

    // TopBar
    val (navController, setNavController) = remember { mutableStateOf<NavController?>(null) }
    val (isBackPressed, setBackPressed) = remember { mutableStateOf(false) }

    // context
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (wordViewModel.isClose) {
            wordViewModel.isClose = false
            wordViewModel.loadWords()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            wordViewModel.closeView()
        }
    }

    BackConfirmHandler(
        isBackPressed = isBackPressed,
        onConfirmMessage = stringResource(id = R.string.positive_button_message),
        onConfirm = {
            setBackPressed(false)
            popUpBackStack()
        },
        onDismissMessage = stringResource(id = R.string.nagative_button_message),
        onDismiss = {
            setBackPressed(false)
        },
        title = stringResource(id = R.string.confirm_dialog_default_message),
    )
    BackHandler(onBack = { setBackPressed(true) })

    TopbarHandler(
        isVisible = true,
        title = context.getString(R.string.word_note),
        onBack = { nav ->
            setBackPressed(true)
            setNavController(nav)
        },
    )

    when (wordNoteState) {
        is WordNoteState.Success -> {
            WordNoteScreenContent(
                modifier = modifier,
                context = context,
                wordNoteState = wordNoteState as WordNoteState.Success,
                navigateToQuiz = navigateToStudy,
                onClick = {
                    navigateToIncorrectWord()
                },
            )
        }

        is WordNoteState.Error -> {
            // api 에러일 때
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = context.getString(R.string.study_load_error_message),
                    textAlign = TextAlign.Center,
                )
            }
        }

        WordNoteState.Loading -> LoadingDialog()
    }
}

@Composable
fun WordNoteScreenContent(
    modifier: Modifier,
    context: Context,
    wordNoteState: WordNoteState.Success,
    navigateToQuiz: (Long) -> Unit = {},
    onClick: () -> Unit = {},
) {
    var selectedWordIndex by remember { mutableStateOf<Int?>(null) }

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = White),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            NoteChangeButton(
                context = context,
                isWordScreen = true,
                onClick = onClick,
            )
            Box(modifier = Modifier.weight(1f)) {
                GridWordList(
                    wordList = wordNoteState.wordList,
                    onWordClick = { index ->
                        selectedWordIndex = index
                    },
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                LongBlackButton(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    onClick = { navigateToQuiz(-1) },
                    text = context.getString(R.string.go_to_study_quiz),
                )
            }
            Spacer(modifier = Modifier.height(45.dp))
        }
    }

    selectedWordIndex?.let { index ->
        WordCardDialog(
            index = index,
            wordCards = wordNoteState.wordList,
            onDismiss = { selectedWordIndex = null },
        )
    }
}

@Composable
fun GridWordList(
    wordList: List<CorrectWord>,
    onWordClick: (Int) -> Unit,
) {
    LazyVerticalGrid(
        modifier = Modifier.padding(horizontal = 12.dp),
        columns = GridCells.Fixed(3),
        state = rememberLazyGridState(),
    ) {
        items(wordList.size, span = {
            GridItemSpan(1)
        }) { index ->
            val word = wordList[index]
            WordListItem(
                word = word,
                onClick = {
                    onWordClick(index)
                },
            )
        }
    }
}

@Composable
fun WordListItem(
    word: CorrectWord,
    onClick: () -> Unit,
) {
    Surface(
        modifier =
            Modifier
                .fillMaxWidth(0.3f)
                .height(110.dp)
                .padding(horizontal = 5.dp, vertical = 8.dp)
                .shadow(
                    elevation = 5.dp,
                    shape = RoundedCornerShape(15.dp),
                ).clickable(onClick = onClick),
        shadowElevation = 10.dp,
        shape = RoundedCornerShape(15.dp),
        color = BackGround,
        border = BorderStroke(width = 2.dp, color = Gray6),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = word.word, style = Typography.labelLarge)
            Spacer(modifier = Modifier.height(5.dp))
            PosBox(pos = word.pos)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WordNotePreview() {
//    WordNoteScreen()
    WordListItem(
        word =
            CorrectWord(
                word = "괄목",
                pos = "명사",
                meaning = "뜻",
                example = "예시",
            ),
    ) {
    }
}
