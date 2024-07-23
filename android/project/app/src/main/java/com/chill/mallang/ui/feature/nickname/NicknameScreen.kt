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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chill.mallang.R
import com.chill.mallang.ui.component.LongBlackButton
import com.chill.mallang.ui.theme.BackGround
import com.chill.mallang.ui.theme.Gray3
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Sub1
import com.chill.mallang.ui.theme.Typography
import com.chill.mallang.ui.util.addFocusCleaner

@Composable
fun NicknameScreen(
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {}
) {
    val nicknameViewModel: NicknameViewModel = hiltViewModel()
    val nicknameState = nicknameViewModel.nicknameState
    val focusManager = LocalFocusManager.current

    Surface(
        color = BackGround,
        modifier = modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager)
    ) {
        NickNameContent(
            modifier = modifier,
            focusManager = focusManager,
            uiState = nicknameState,
            onClick = { onClick(it) },
        )
    }
}

@Composable
fun NickNameContent(
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current,
    uiState: NicknameState = NicknameState(),
    onClick: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_title_small),
            contentDescription = null,
            modifier = Modifier.height(120.dp)
        )
        Spacer(modifier = Modifier.weight(0.2f))
        TextWithIcon(text = "사용할 닉네임을 입력해 주세요", icon = R.drawable.ic_mage)
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextField(
                onValueChange = {
                    uiState.updateNickname(it)
                },
                focusManager = focusManager,
                nicknameState = uiState,
                onClearPressed = {
                    uiState.clearNickname()
                }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        LongBlackButton(
            onClick = { onClick(uiState.nickname) },
            text = "결정하기"
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    focusManager: FocusManager,
    placeholder: String = "닉네임",
    nicknameState: NicknameState,
    onClearPressed: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = nicknameState.nickname,
            onValueChange = {
                onValueChange(it)
            },
            placeholder = {
                if (nicknameState.nickname.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = Typography.displayMedium
                    )
                }
            },
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Gray6,
                unfocusedBorderColor = Gray3,
                focusedContainerColor = White,
                unfocusedContainerColor = White,
                focusedTextColor = Gray6,
                unfocusedTextColor = Gray6,
                unfocusedPlaceholderColor = Gray3,
                focusedPlaceholderColor = Gray3
            ),
            textStyle = Typography.displayMedium,
            trailingIcon = {
                IconButton(
                    onClick = onClearPressed
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cancel),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            },
            isError = nicknameState.errorMessage != ""
        )
        Text(
            text = nicknameState.errorMessage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, start = 4.dp),
            color = Sub1,
            style = Typography.displayMedium
        )
    }
}

@Composable
fun TextWithIcon(
    modifier: Modifier = Modifier,
    text: String,
    icon: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = Typography.headlineMedium
        )
        Box(modifier = Modifier.width(3.dp))
        Image(
            painter = painterResource(id = icon),
            contentDescription = null
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
