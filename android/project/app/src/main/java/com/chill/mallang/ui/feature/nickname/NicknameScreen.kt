package com.chill.mallang.ui.feature.nickname

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.chill.mallang.R
import com.chill.mallang.ui.component.CustomSnackBar
import com.chill.mallang.ui.component.LongBlackButton
import com.chill.mallang.ui.theme.BackGround
import com.chill.mallang.ui.theme.Gray3
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Sub1
import com.chill.mallang.ui.theme.Typography
import com.chill.mallang.ui.util.addFocusCleaner
import kotlinx.coroutines.launch

@Composable
fun NicknameScreen(
    modifier: Modifier = Modifier,
    onSuccess: (String) -> Unit = {},
    viewModel: NicknameViewModel = hiltViewModel(),
    popUpBackStack: () -> Unit = {},
) {
    val nicknameState = viewModel.nicknameState
    val focusManager = LocalFocusManager.current
    val nickNameUiState by viewModel.uiState.collectAsStateWithLifecycle()

    // SnackBarHostState 생성
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    HandleNickNameUiEvent(uiState = nickNameUiState, onSuccess = onSuccess)

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                snackbar = { snackBarData ->
                    CustomSnackBar(
                        snackBarData = snackBarData,
                        backgroundColor = Gray6,
                        textColor = White,
                    )
                },
            )
        },
    ) { innerPadding ->
        Surface(
            color = BackGround,
            modifier =
            modifier
                .fillMaxSize()
                .padding(innerPadding)
                .addFocusCleaner(focusManager),
        ) {
            NickNameContent(
                focusManager = focusManager,
                uiState = nicknameState,
                checkNickName = {
                    // 정규식에 맞는 경우에만 checkNickName() 실행
                    if (nicknameState.errorMessage == "") {
                        viewModel.checkNickName()
                    } else {
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                message = nicknameState.errorMessage,
                                duration = SnackbarDuration.Short,
                            )
                        }
                    }
                },
            )
        }
    }
}

@Composable
fun HandleNickNameUiEvent(
    uiState: NickNameUiState,
    onSuccess: (String) -> Unit = {},
) {
    LaunchedEffect(uiState) {
        when (uiState) {
            is NickNameUiState.Success -> onSuccess(uiState.nickName)

            else -> {}
        }
    }
}

@Composable
fun NickNameContent(
    focusManager: FocusManager = LocalFocusManager.current,
    uiState: NicknameState = NicknameState(),
    checkNickName: () -> Unit = { },
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.login_background))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_title),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(0.6f),
            )
            Spacer(modifier = Modifier.weight(0.2f))
            TextWithIcon(
                text = stringResource(R.string.login_info_message),
                icon = R.drawable.ic_mage,
            )
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CustomTextField(
                    onValueChange = {
                        uiState.updateNickname(it)
                    },
                    focusManager = focusManager,
                    nickName = uiState.nickname,
                    errorMessage = uiState.errorMessage,
                    onClearPressed = {
                        uiState.clearNickname()
                    },
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            LongBlackButton(
                onClick = {
                    checkNickName()
                },
                text = stringResource(R.string.button_decide_message),
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    focusManager: FocusManager,
    placeholder: String = stringResource(R.string.nickname_place_holder),
    nickName: String = "",
    errorMessage: String = "",
    onClearPressed: () -> Unit = {},
) {
    Column(
        modifier =
        Modifier
            .fillMaxWidth(0.8f),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedTextField(
            value = nickName,
            onValueChange = {
                onValueChange(it)
            },
            placeholder = {
                if (nickName.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = Typography.displayMedium,
                    )
                }
            },
            modifier =
            modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            keyboardActions =
            KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            colors =
            OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Gray6,
                unfocusedBorderColor = Gray3,
                focusedContainerColor = White,
                unfocusedContainerColor = White,
                errorContainerColor = White, // 에러 상태에서도 배경색이 변경되지 않도록 White 설정
                focusedTextColor = Gray6,
                unfocusedTextColor = Gray6,
                unfocusedPlaceholderColor = Gray3,
                focusedPlaceholderColor = Gray3,
            ),
            textStyle = Typography.displayMedium,
            trailingIcon = {
                IconButton(
                    modifier = Modifier.testTag("clear_button"),
                    onClick = onClearPressed,

                    ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cancel),
                        contentDescription = "clear_button",
                        tint = Color.Unspecified,
                    )
                }
            },
            isError = errorMessage != "",
        )
        Text(
            text = errorMessage,
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, start = 4.dp),
            color = Sub1,
            style = Typography.displayMedium,
        )
    }
}

@Composable
fun TextWithIcon(
    modifier: Modifier = Modifier,
    text: String,
    icon: Int,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Text(
            text = text,
            style = Typography.headlineMedium,
        )
        Box(modifier = Modifier.width(3.dp))
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NicknameScreenPreview() {
    MallangTheme {
        NickNameContent()
    }
}
