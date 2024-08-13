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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.chill.mallang.ui.component.LoadingDialog
import com.chill.mallang.ui.component.ReloadEffect
import com.chill.mallang.ui.component.experiencebar.ExperienceState
import com.chill.mallang.ui.feature.home.layout.BottomButtonHolder
import com.chill.mallang.ui.feature.home.layout.HomeBackground
import com.chill.mallang.ui.feature.setting.EditNickNameDialogScreen
import com.chill.mallang.ui.feature.setting.SettingDialog
import com.chill.mallang.ui.feature.topbar.TopbarHandler
import com.chill.mallang.ui.theme.Gray2
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Sub1
import com.chill.mallang.ui.theme.Typography
import com.chill.mallang.ui.util.noRippleClickable
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToWordNote: () -> Unit = {},
    navigateToGame: () -> Unit = {},
    popUpBackStack: () -> Unit = {},
    onShowErrorSnackBar: (String) -> Unit = {},
    exitApplication: () -> Unit = {},
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val (showSettingDialog, setShowSettingDialog) =
        rememberSaveable {
            mutableStateOf(false)
        }
    val (showEditNickNameDialog, setShowEditNickNameDialog) =
        rememberSaveable {
            mutableStateOf(false)
        }

    ReloadEffect(
        onLoad = viewModel::getUserInfo
    )

    // TopBar
    TopbarHandler()

    HandleHomeUiEvent(
        event = viewModel.event,
        setShowSettingDialog = setShowSettingDialog,
        setShowEditNickNameDialog = setShowEditNickNameDialog,
        loadUserInfo = viewModel::getUserInfo,
        onShowErrorSnackBar = onShowErrorSnackBar,
        popUpBackStack = popUpBackStack,
        exitApplication = exitApplication,
    )

    DisposableEffect(Unit) {
        viewModel.playBGM()
        onDispose {
            viewModel.stopBGM()
        }
    }

    Box(
        modifier =
        Modifier
            .fillMaxSize()
    ) {
        HomeContent(
            modifier = modifier,
            uiState = uiState,
            navigateToGame = navigateToGame,
            navigateToWordNote = navigateToWordNote,
            sendEvent = { viewModel.sendEvent(it) },
            onShowSettingDialog = showSettingDialog,
            onShowEditNickNameDialog = showEditNickNameDialog,
            onSignOut = viewModel::signOut,
            onLogOut = viewModel::logout,
            exitApplication = exitApplication,
        )
    }
}

@Composable
fun HandleHomeUiEvent(
    event: SharedFlow<HomeUiEvent>,
    setShowSettingDialog: (Boolean) -> Unit,
    setShowEditNickNameDialog: (Boolean) -> Unit,
    popUpBackStack: () -> Unit,
    loadUserInfo: () -> Unit,
    onShowErrorSnackBar: (String) -> Unit,
    exitApplication: () -> Unit,
) {
    LaunchedEffect(Unit) {
        event.collectLatest { homeUiEvent ->
            when (homeUiEvent) {
                HomeUiEvent.CloseEditNickNameDialog -> setShowEditNickNameDialog(false)
                HomeUiEvent.CloseSettingDialog -> setShowSettingDialog(false)
                HomeUiEvent.ShowEditNickNameDialog -> setShowEditNickNameDialog(true)
                HomeUiEvent.ShowSettingDialog -> setShowSettingDialog(true)
                HomeUiEvent.Refresh -> loadUserInfo()
                is HomeUiEvent.Error -> onShowErrorSnackBar(homeUiEvent.errorMessage)
                is HomeUiEvent.SignOut -> {
                    setShowSettingDialog(false)
                    onShowErrorSnackBar(homeUiEvent.message)
                    popUpBackStack()
                }

                is HomeUiEvent.Logout -> {
                    onShowErrorSnackBar(homeUiEvent.message)
                    setShowSettingDialog(false)
                    popUpBackStack()
                }
            }
        }
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    navigateToWordNote: () -> Unit,
    navigateToGame: () -> Unit,
    sendEvent: (HomeUiEvent) -> Unit,
    onShowSettingDialog: Boolean,
    onShowEditNickNameDialog: Boolean,
    onSignOut: () -> Unit,
    onLogOut: () -> Unit,
    exitApplication: () -> Unit,
) {
    when (uiState) {
        is HomeUiState.Loading -> LoadingDialog()

        is HomeUiState.LoadUserInfo -> {
            HomeScreenContent(
                modifier = modifier,
                uiState = uiState,
                navigateToGame = navigateToGame,
                navigateToWordNote = navigateToWordNote,
                exitApplication = exitApplication,
                onClickSetting = { sendEvent(HomeUiEvent.ShowSettingDialog) },
            )
            if (onShowSettingDialog) {
                SettingDialog(
                    onClose = { sendEvent(HomeUiEvent.CloseSettingDialog) },
                    onShowEditNickNameDialog = { sendEvent(HomeUiEvent.ShowEditNickNameDialog) },
                    onLogOut = onLogOut,
                    onSignOut = onSignOut,
                )
            }
            if (onShowEditNickNameDialog) {
                EditNickNameDialogScreen(
                    onDismiss = { onEditNickName ->
                        sendEvent(HomeUiEvent.CloseEditNickNameDialog)
                        if (onEditNickName != uiState.nickName) {
                            sendEvent(HomeUiEvent.Refresh)
                        }
                    },
                    userNickName = uiState.nickName,
                )
            }
        }
    }
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    uiState: HomeUiState.LoadUserInfo,
    navigateToWordNote: () -> Unit = {},
    navigateToGame: () -> Unit = {},
    exitApplication: () -> Unit = {},
    onClickSetting: () -> Unit = {},
) {
    val isBackPressed = remember { mutableStateOf(false) }
    BackConfirmHandler(
        isBackPressed = isBackPressed.value,
        onConfirmMessage = stringResource(id = R.string.positive_button_message),
        onConfirm = {
            isBackPressed.value = false
            exitApplication()
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
    Column {
        HomeBackground(
            modifier = Modifier.weight(1f),
            nickName = uiState.nickName,
            factionId = uiState.factionId,
            experienceState = uiState.experienceState)
        BottomButtonHolder(
            onClickStudy = navigateToWordNote,
            onClickMap = navigateToGame,
            onClickSetting = onClickSetting,
        )
    }
}

@Composable
fun ImageButton(
    modifier: Modifier = Modifier,
    icon: Int,
    label: String,
    onClick: () -> Unit,
) {
    Column(
        modifier =
            modifier.noRippleClickable {
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
    userFaction: Long,
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
        HomeScreenContent(uiState = HomeUiState.LoadUserInfo("짜이한", 1, ExperienceState.Static(0.5f, 1)))
    }
}
