package com.chill.mallang.ui.feature.incorrect_word

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import com.chill.mallang.ui.component.NoteChangeButton
import com.chill.mallang.ui.feature.topbar.TopbarHandler
import com.chill.mallang.ui.theme.BackGround
import com.chill.mallang.ui.theme.Gray4
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.Typography

@Composable
fun IncorrectNoteScreen(
    modifier: Modifier = Modifier,
    popUpBackStack: () -> Unit = {},
    navigateToWordNote: () -> Unit = {},
    navigateToStudy: (Long) -> Unit = {},
    incorrectViewModel: IncorrectWordViewModel = hiltViewModel(),
) {
    val incorrectState by incorrectViewModel.incorrectNoteState.collectAsStateWithLifecycle()

    // TopBar
    val (navController, setNavController) = remember { mutableStateOf<NavController?>(null) }
    val (isBackPressed, setBackPressed) = remember { mutableStateOf(false) }

    // context
    val context = LocalContext.current

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
        title = context.getString(R.string.incorrect_word_note),
        onBack = { nav ->
            setBackPressed(true)
            setNavController(nav)
        },
    )

    when (incorrectState) {
        is IncorrectNoteState.Success -> {
            IncorrectContent(
                context = context,
                incorrectState = incorrectState as IncorrectNoteState.Success,
                navigateToStudy = navigateToStudy,
                onClick = {
                    navigateToWordNote()
                },
            )
        }

        is IncorrectNoteState.Error -> {
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

        IncorrectNoteState.Loading -> LoadingDialog()
    }
}

@Composable
fun IncorrectContent(
    context: Context,
    incorrectState: IncorrectNoteState.Success,
    navigateToStudy: (Long) -> Unit,
    onClick: () -> Unit,
) {
    var selectedWordIndex by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
    ) {
        NoteChangeButton(
            context = context,
            isWordScreen = false,
            onClick = onClick,
        )
        if (incorrectState.wordList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = context.getString(R.string.no_incorrect_word_message),
                    textAlign = TextAlign.Center,
                )
            }
        } else {
            Box(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = context.getString(R.string.incorrect_note_script),
                    style = Typography.bodyMedium,
                    color = Gray4,
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            IncorrectList(
                list = incorrectState.wordList,
                onClick = { index ->
                    selectedWordIndex = index
                },
            )

            // 오답노트일 때는 그때 풀었던 거 보여줌.
            selectedWordIndex?.let { index ->
                val word = incorrectState.wordList[index]
                navigateToStudy(word.studyId)
            }
        }
    }
}

@Composable
fun IncorrectList(
    list: List<IncorrectWord>,
    onClick: (Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        items(list.size) { index ->
            val incorrectWord = list[index]
            IncorrectListItem(
                incorrectWord = incorrectWord,
                onClick = { onClick(index) },
            )
        }
    }
}

@Composable
fun IncorrectListItem(
    incorrectWord: IncorrectWord,
    onClick: () -> Unit,
) {
    Surface(
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .shadow(
                    elevation = 3.dp,
                    shape = RoundedCornerShape(15.dp),
                    clip = false,
                ).clickable(onClick = onClick),
        shape = RoundedCornerShape(15.dp),
        color = BackGround,
        border = BorderStroke(width = 2.dp, color = Gray6),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 30.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier.padding(3.dp),
                text = incorrectWord.script,
                style = Typography.headlineSmall,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ListItemPrivew() {
    IncorrectListItem(
        incorrectWord =
            IncorrectWord(
                studyId = 1,
                script = "여기에 문장 문장이 들어갈 거예요 __ 여기요.",
            ),
        onClick = {},
    )
}
