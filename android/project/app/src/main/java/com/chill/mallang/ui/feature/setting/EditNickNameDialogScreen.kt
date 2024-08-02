package com.chill.mallang.ui.feature.setting

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.chill.mallang.R
import com.chill.mallang.ui.component.LongBlackButton
import com.chill.mallang.ui.feature.home.ImageButton
import com.chill.mallang.ui.feature.nickname.CustomTextField
import com.chill.mallang.ui.feature.nickname.NicknameState
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography

@Composable
fun EditNickNameDialogScreen(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onChangeNickName: () -> Unit = {},
) {
    EditNickNameDialogContent(
        modifier = modifier,
        onDismiss = onDismiss,
        onChangeNickName = onChangeNickName,
    )
}

@Composable
fun EditNickNameDialogContent(
    modifier: Modifier = Modifier,
    uiState: NicknameState = NicknameState(),
    onDismiss: () -> Unit = {},
    onChangeNickName: () -> Unit = {},
) {
    val (nickName, setNickName) =
        remember {
            mutableStateOf("")
        }
    val focusManager = LocalFocusManager.current
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            shape = RoundedCornerShape(15.dp),
            border = BorderStroke(width = 2.dp, color = Gray6),
            color = Color.White,
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text(text = stringResource(R.string.edit_nick_name))
                    }
                    Box(
                        modifier =
                            Modifier
                                .weight(1f),
                        contentAlignment = Alignment.TopEnd,
                    ) {
                        ImageButton(
                            icon = R.drawable.ic_close,
                            label = "",
                            onClick = onDismiss,
                        )
                    }
                }
                CustomTextField(
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { setNickName(it) },
                    focusManager = focusManager,
                    nickName = nickName,
                    onClearPressed = { setNickName("") },
                    errorMessage = "",
                )
                Text(
                    text = stringResource(R.string.nick_name_validation_info),
                    style = Typography.bodySmall,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row {
                    LongBlackButton(
                        modifier =
                            Modifier
                                .weight(1f)
                                .height(40.dp),
                        onClick = onChangeNickName,
                        text = stringResource(R.string.edit_nick_name_confirm),
                    )
                    Spacer(modifier = Modifier.weight(0.5f))
                    LongBlackButton(
                        modifier =
                            Modifier
                                .weight(1f)
                                .height(40.dp),
                        onClick = onDismiss,
                        text = stringResource(R.string.edit_nick_name_dismiss),
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun EditNickNameDialogPreview() {
    MallangTheme {
        EditNickNameDialogContent()
    }
}
