package com.chill.mallang.ui.feature.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chill.mallang.R
import com.chill.mallang.ui.component.BackConfirmHandler
import com.chill.mallang.ui.theme.Gray2
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Sub1
import com.chill.mallang.ui.theme.Typography
import com.chill.mallang.ui.util.noRippleClickable

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToWordNote: () -> Unit = {},
    navigateToGame: () -> Unit = {},
    popUpBackStack: () -> Unit = {},
    onShowErrorSnackBar: (String) -> Unit = {},
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Box(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp),
    ) {
        HomeContent(
            modifier = modifier,
            uiState = uiState,
            navigateToGame = navigateToGame,
            navigateToWordNote = navigateToWordNote,
            popUpBackStack = popUpBackStack,
            onShowErrorSnackBar = onShowErrorSnackBar,
        )
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    navigateToWordNote: () -> Unit,
    navigateToGame: () -> Unit,
    popUpBackStack: () -> Unit,
    onShowErrorSnackBar: (String) -> Unit,
) {
    LaunchedEffect(uiState) {
        if (uiState is HomeUiState.Error) {
            onShowErrorSnackBar(uiState.errorMessage)
        }
    }
    when (uiState) {
        is HomeUiState.Loading -> {} // 로딩 화면

        is HomeUiState.LoadUserInfo -> {
            HomeScreenContent(
                modifier = modifier,
                userNickName = uiState.userNickName,
                userFaction = uiState.userFaction,
                navigateToGame = navigateToGame,
                navigateToWordNote = navigateToWordNote,
                popUpBackStack = popUpBackStack,
            )
        }

        is HomeUiState.Error -> {}
    }
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    userNickName: String = "",
    userFaction: String = "",
    navigateToWordNote: () -> Unit = {},
    navigateToGame: () -> Unit = {},
    popUpBackStack: () -> Unit = {},
) {
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
        content = stringResource(R.string.app_exit_message),
    )
    BackHandler(onBack = {
        isBackPressed.value = true
    })
    Column {
        Row {
            UserItem(
                icon = R.drawable.ic_stars,
                label = "15코인",
            )
            Spacer(modifier.width(5.dp))
            UserItem(
                icon = R.drawable.ic_stars,
                label = "15코인",
            )
        }
        SideUserButton() // 사이드 버튼
        UserCharacter(
            // 유저 캐릭터 or 프로필
            modifier = modifier,
            userNickName = userNickName,
            userFaction = userFaction,
        )
        ModeButton(
            // 퀴즈 모드 버튼
            icon = R.drawable.ic_question,
            label = stringResource(R.string.mode_quiz),
            modifier = Modifier.align(Alignment.End),
            onClick = { navigateToWordNote() },
        )
        Spacer(modifier.weight(0.05f))
        ModeButton(
            // 점령전 모드 버튼
            icon = R.drawable.ic_location,
            label = stringResource(R.string.mode_game),
            modifier = Modifier.align(Alignment.End),
            onClick = { navigateToGame() },
        )
        Spacer(modifier.weight(0.3f))
    }
}

@Composable
fun ImageButton(
    icon: Int,
    label: String,
    onClick: () -> Unit,
) {
    Column(
        modifier =
        Modifier.noRippleClickable {
            onClick()
        },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = label,
        )
        Text(
            text = label,
            style = Typography.displayLarge,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(5.dp))
    }
}

@Composable
fun SideUserButton(modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End,
    ) {
        ImageButton(
            icon = R.drawable.ic_setting,
            label = stringResource(R.string.side_button_setting),
            onClick = { },
        )
        ImageButton(
            icon = R.drawable.ic_quest,
            label = stringResource(R.string.side_button_quest),
            onClick = { },
        )
        ImageButton(
            icon = R.drawable.ic_ranking,
            label = stringResource(R.string.side_button_ranking),
            onClick = { },
        )
    }
}

@Composable
internal fun UserItem(
    icon: Int,
    label: String,
) {
    Row(
        modifier =
        Modifier
            .border(1.dp, Color.Black, shape = RoundedCornerShape(15.dp))
            .padding(5.dp)
            .height(IntrinsicSize.Min),
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = label,
            tint = Color.Yellow,
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 4.dp),
        )
        Text(
            text = label,
            style = Typography.displaySmall,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 5.dp),
        )
    }
}

@Composable
fun UserCharacter(
    modifier: Modifier = Modifier,
    userNickName: String,
    userFaction: String,
) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_stars),
                contentDescription = "",
                modifier = Modifier.align(Alignment.CenterVertically),
            )
            Text(
                text = userNickName,
                style = Typography.headlineLarge,
                modifier = Modifier.padding(start = 3.dp),
            )
        }
        Image(painter = painterResource(id = R.mipmap.malang), contentDescription = "말랑")
        Box(modifier = Modifier.height(IntrinsicSize.Max)) {
            Image(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(id = R.mipmap.rectangle_message),
                contentDescription = "",
            )
            Text(
                modifier =
                Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.Center),
                text = stringResource(id = R.string.character_message),
                style = Typography.bodyLarge,
                color = Sub1,
                fontSize = 14.sp,
            )
        }
    }
}

@Composable
fun ModeButton(
    icon: Int,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier =
        modifier
            .width(75.dp)
            .height(75.dp)
            .noRippleClickable { onClick() }
            .background(color = Gray2, shape = CircleShape)
            .border(width = 2.dp, color = Color.Black, shape = CircleShape),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = label,
        )
        Text(
            text = label,
            style = Typography.displayMedium,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomePreview() {
    MallangTheme {
        HomeScreenContent(userNickName = "짜이한")
    }
}
