package com.chill.mallang.ui.feature.fort_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.R
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography

@Composable
fun FortDetailScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        Header(
            modifier = Modifier.weight(1F)
        )
        MainBody(
            modifier = Modifier.weight(6F)
        )
        GameStartBody(
            modifier = Modifier.weight(2F)
        )
        RecordBody(
            modifier = Modifier.weight(7F)
        )
    }
}

@Composable
fun Header(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.LightGray.copy(alpha = 0.4f))
            .padding(vertical = 7.dp, horizontal = 10.dp)
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = "",
                modifier = Modifier.height(height = 20.dp)
            )
            Text(text = "구미 캠퍼스", fontSize = 18.sp)
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_exit),
            contentDescription = "",
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}

@Composable
fun MainBody(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = 20.dp, horizontal = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            MyTeamInfo(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxHeight()
            )
            Text("VS", fontSize = 40.sp)
            CompTeamInfo(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxHeight()
            )
        }
    }
}

@Composable
fun MyTeamInfo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = "[진평짱]", color = Color.Red)
        Image(
            painter = painterResource(id = R.mipmap.malang),
            contentDescription = ""
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_master_tier),
                contentDescription = "",
                modifier = Modifier.height(30.dp)
            )
            Text(
                text = "나갱갱",
                style = Typography.displayLarge,
                fontSize = 28.sp
            )
        }
        Text(
            text = "총점 3990",
            fontSize = 30.sp,
            style = Typography.displayLarge,
            color = Color.Red
        )
    }
}

@Composable
fun CompTeamInfo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = "[인동짱]", color = Color.Blue)
        Image(
            painter = painterResource(id = R.mipmap.malang),
            contentDescription = ""
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_master_tier),
                contentDescription = "",
                modifier = Modifier.height(30.dp)
            )
            Text(
                text = "짜이한",
                style = Typography.displayLarge,
                fontSize = 28.sp
            )
        }
        Text(
            text = "총점 3290",
            fontSize = 30.sp,
            style = Typography.displayLarge,
            color = Color.Blue
        )
    }
}

@Composable
fun GameStartBody(modifier: Modifier = Modifier){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
        , horizontalAlignment = Alignment.CenterHorizontally
        , verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 100.dp),
            onClick = { },
            shape = RoundedCornerShape(10.dp), // 둥근 모서리
            colors = ButtonDefaults.buttonColors(
                Color.Black,
                Color.White
            )
        ) {
            Text("게임 시작", style = Typography.displayLarge, fontSize = 30.sp)
        }
        Text(text = "남은 기회: 3회")
    }
}

@Composable
fun RecordBody(modifier: Modifier = Modifier) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("우리팀 기록", "상대팀 기록")

    Column(
        modifier = modifier
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            indicator = {},
            divider = {}
        ) {
            tabs.forEachIndexed { index, title ->
                Box {
                    CustomBorderBox(
                        bottomBorderColor = if (selectedTabIndex == index) { Color.White } else { Color.Black },
                        bottomBorderWidth = if (selectedTabIndex == index) { 4.dp } else { 1.dp }
                    )
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) },
                    )
                }
            }
        }
        when (selectedTabIndex) {
            0 -> MyTeamTab()
            1 -> CompTeamTab()
        }
    }
}

@Composable
fun MyTeamTab(modifier: Modifier = Modifier) {
    val items = List(10) { index -> "Item #$index" }

    LazyColumn(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .padding(vertical = 10.dp)
    ) {
        items(items) { item ->
            RecordListItem()
        }
    }
}

@Composable
fun CompTeamTab(modifier: Modifier = Modifier) {
    val items = List(10) { index -> "Item #$index" }

    LazyColumn(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .padding(vertical = 10.dp)
    ) {
        items(items) { item ->
            RecordListItem()
        }
    }
}

@Composable
fun RecordListItem(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .height(height = 65.dp)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .border(1.dp, Color.Black, shape = RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "1위",
                fontSize = 24.sp,
                style = Typography.displayLarge,
                color = Color.Red
            )
            Text(
                text = "짜이한",
                fontSize = 22.sp,
                style = Typography.displayLarge,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "100점",
                fontSize = 22.sp,
                style = Typography.displayLarge,
            )
        }
    }
}

@Composable
fun CustomBorderBox(
    modifier: Modifier = Modifier,
    bottomBorderColor: Color = Color.White,
    bottomBorderWidth: Dp = 2.dp,
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp
                    )
                )
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 2.dp)
                .height(bottomBorderWidth)
                .fillMaxWidth()
                .background(bottomBorderColor)
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun FortDetailScreenPreview() {
    MallangTheme {
        FortDetailScreen()
    }
}
