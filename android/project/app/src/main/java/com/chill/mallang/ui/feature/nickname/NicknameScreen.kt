package com.chill.mallang.ui.feature.nickname

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chill.mallang.R
import com.chill.mallang.ui.component.LongBlackButton
import com.chill.mallang.ui.theme.BackGround
import com.chill.mallang.ui.theme.Gray3
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography
import com.chill.mallang.ui.util.addFocusCleaner

@Composable
fun NicknameScreen() {
    val focusManager = LocalFocusManager.current
    var nickname by remember { mutableStateOf(TextFieldValue("")) }

    Surface(
        color = BackGround,
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_title_small),
                contentDescription = null,
                modifier = Modifier.fillMaxHeight(0.17f)
            )
            Spacer(modifier = Modifier.weight(0.1f))
            TextWithIcon(text = "사용할 닉네임을 입력해 주세요", icon = R.drawable.ic_mage)
            Box(modifier = Modifier.height(15.dp))
            CustomTextField(
                value = nickname,
                onValueChange = { nickname = it },
                focusManager = focusManager
            )
            Box(modifier = Modifier.height(20.dp))
            LongBlackButton(onClick = { }, text = "결정하기")
            Spacer(modifier = Modifier.weight(0.7f))
        }
    }
}

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    focusManager: FocusManager,
    placeholder: String = "닉네임"
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            if (value.text.isEmpty()) {
                Text(
                    text = placeholder,
                    style = Typography.displayMedium
                )
            }
        },
        modifier = modifier
            .fillMaxWidth(0.8f)
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
        textStyle = Typography.displayMedium
    )
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
        NicknameScreen()
    }
}
