package com.chill.mallang.ui.feature.home

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.R
import com.chill.mallang.ui.theme.Gray2
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Sub1
import com.chill.mallang.ui.theme.Typography
import com.chill.mallang.ui.util.noRippleClickable


object TestData { // 화면 임시 구성할 데이터
    const val USER_NAME = "짜이한"
    const val USER_RANK = "다이아"
    const val USER_ITEM = "15코인"
    const val RANK_1 = "다이아"
    const val RANK_2 = "골드"
    const val RANK_3 = "실버"
    const val RANK_4 = "브론즈"
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Column {
            Row { // 유저 아이템 수의 따라 LazyColumn
                UserItem(
                    icon = R.drawable.ic_stars,
                    label = TestData.USER_ITEM
                )
                Spacer(modifier.width(5.dp))
                UserItem(
                    icon = R.drawable.ic_stars,
                    label = TestData.USER_ITEM
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            SideUserButton() // 사이드 버튼
            UserCharacter( // 유저 캐릭터 or 프로필
                modifier = modifier,
                userName = TestData.USER_NAME,
                userRank = TestData.USER_RANK
            )
            Spacer(modifier.weight(0.15f))
            ModeButton( // 퀴즈 모드 버튼
                icon = R.drawable.ic_question,
                label = stringResource(R.string.mode_quiz),
                modifier = Modifier.align(Alignment.End),
                onClick = { }
            )
            Spacer(modifier.weight(0.05f))
            ModeButton( // 점령전 모드 버튼
                icon = R.drawable.ic_location,
                label = stringResource(R.string.mode_home),
                modifier = Modifier.align(Alignment.End),
                onClick = { }
            )
            Spacer(modifier.weight(0.15f))
        }
    }
}

@Composable
fun IconButton(
    icon: Int,
    label: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.noRippleClickable {
            onClick()
        },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = label
        )
        Text(
            text = label,
            style = Typography.displayLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SideUserButton(modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        IconButton(
            icon = R.drawable.ic_setting,
            label = stringResource(R.string.side_button_setting),
            onClick = { }
        )
        Spacer(modifier = modifier.size(15.dp))
        IconButton(
            icon = R.drawable.ic_quest,
            label = stringResource(R.string.side_button_quest),
            onClick = { })
        Spacer(modifier = modifier.size(15.dp))
        IconButton(
            icon = R.drawable.ic_ranking,
            label = stringResource(R.string.side_button_ranking),
            onClick = { })
    }
}

@Composable
internal fun UserItem(icon: Int, label: String) {
    Row(
        modifier = Modifier
            .border(1.dp, Color.Black, shape = RoundedCornerShape(15.dp))
            .padding(5.dp)
            .height(IntrinsicSize.Min)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = label,
            tint = Color.Yellow,
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 4.dp)
        )
        Text(
            text = label,
            style = Typography.displaySmall,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 5.dp)
        )
    }
}

@Composable
fun UserCharacter(modifier: Modifier = Modifier, userName: String, userRank: String) {
    when (userRank) { // 티어별 이미지 할당
        TestData.RANK_1 -> {}
        TestData.RANK_2 -> {}
        TestData.RANK_3 -> {}
        TestData.RANK_4 -> {}
    }
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_stars),
                contentDescription = userRank,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Text(
                text = userName,
                style = Typography.headlineLarge,
                modifier = Modifier.padding(start = 3.dp)
            )
        }
        Image(painter = painterResource(id = R.mipmap.malang), contentDescription = "말랑")
        Box(modifier = Modifier.height(IntrinsicSize.Max)) {
            Image(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(id = R.mipmap.rectangle_message),
                contentDescription = ""
            )
            Text(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.Center),
                text = stringResource(id = R.string.character_message),
                style = Typography.bodyLarge,
                color = Sub1,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun ModeButton(
    icon: Int,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
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
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomePreview() {
    MallangTheme {
        HomeScreen()
    }
}
