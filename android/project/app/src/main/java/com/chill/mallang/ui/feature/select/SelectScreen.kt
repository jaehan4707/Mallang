package com.chill.mallang.ui.feature.select

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chill.mallang.R
import com.chill.mallang.ui.component.LongBlackButton
import com.chill.mallang.ui.component.PercentageBar
import com.chill.mallang.ui.feature.nickname.TextWithIcon
import com.chill.mallang.ui.theme.BackGround
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Red01
import com.chill.mallang.ui.theme.SkyBlue
import com.chill.mallang.ui.theme.Typography

@Composable
fun SelectScreen(navigateToMain: () -> Unit = {}, signUp: (String) -> Unit = {}) {
    val viewModel: SelectViewModel = hiltViewModel()
    var selectedTeam by remember { mutableStateOf<String?>(null) }
    var showConfirmButton by remember { mutableStateOf(false) }
    val isSignUp = viewModel.isSignUp.collectAsStateWithLifecycle()

    if (isSignUp.value) { //회원가입 성공
        navigateToMain()
    }
    Surface(
        color = BackGround
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
            TextWithIcon(text = "당신의 진영을 선택해 주세요", icon = R.drawable.ic_team)
            Spacer(modifier = Modifier.height(30.dp))
            PercentageBar(
                leftPercentage = 30,
                rightPercentage = 70,
                leftLabel = "말",
                rightLabel = "랑",
                leftColor = Red01,
                rightColor = SkyBlue
            )
            Spacer(modifier = Modifier.height(35.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                TeamButton(
                    onClick = {
                        selectedTeam = "말"
                        showConfirmButton = true
                    },
                    text = "말",
                    color = Red01,
                    isSelected = selectedTeam == "말"
                )
                Spacer(modifier = Modifier.width(25.dp))
                TeamButton(
                    onClick = {
                        selectedTeam = "랑"
                        showConfirmButton = true
                    },
                    text = "랑",
                    color = SkyBlue,
                    isSelected = selectedTeam == "랑"
                )
            }
            if (showConfirmButton) {
                Spacer(modifier = Modifier.height(50.dp))
                LongBlackButton(onClick = { viewModel.login(selectedTeam) }, text = "결정하기")
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

// 팀 버튼
@Composable
fun TeamButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    color: Color,
    isSelected: Boolean
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = White,
            containerColor = color
        ),
        shape = RoundedCornerShape(13.dp),
        modifier = modifier
            .width(100.dp)
            .height(48.dp)
            .then(
                if (isSelected) Modifier.border(
                    2.dp,
                    Gray6,
                    RoundedCornerShape(13.dp)
                ) else Modifier
            )
    ) {
        Text(
            text,
            style = Typography.displayLarge
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SelectScreenPreview() {
    MallangTheme {
        SelectScreen()
    }
}
