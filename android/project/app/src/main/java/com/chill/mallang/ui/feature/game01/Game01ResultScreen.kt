package com.chill.mallang.ui.feature.game01

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.R
import com.chill.mallang.ui.feature.fort_detail.RecordListItem
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography

@Composable
fun Game01ResultScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            ScoreBody()
            ResultImageBody()
            ResultTitleBody()
            ResultDetailBody()
            LeaderBoardBody()
            ButtonBox()
        }
    }
}

@Composable
fun ScoreBody(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "300점",
            fontSize = 30.sp
        )
    }
}

@Composable
fun ResultImageBody(modifier: Modifier = Modifier) {
    var isExpanded by remember { mutableStateOf(false) }
    val size by animateDpAsState(
        targetValue = if (isExpanded) 300.dp else 100.dp,
        animationSpec = tween(durationMillis = 1500)
    )

    LaunchedEffect(Unit) {
        isExpanded = true
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .size(size),
        contentAlignment = Alignment.Center,
    ){
        Image(
            modifier = Modifier.size(250.dp),
            painter = painterResource(id = R.drawable.img_clap),
            contentDescription = "",
        )
    }
}

@Composable
fun ResultTitleBody() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "Success !",
            style = Typography.displayLarge,
            fontSize = 70.sp
        )
    }
}

@Composable
fun ResultDetailBody() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "상대팀 보다 18점 앞서고 있습니다!",
            style = Typography.bodySmall,
            fontSize = 24.sp
        )
    }
}

@Composable
fun LeaderBoardBody(modifier: Modifier = Modifier) {
    val items = List(10) { index -> "Item #$index" }

    Column(
        modifier = modifier
            .padding(10.dp)
            .border(width = 2.dp, color = Gray6, shape = RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp, vertical = 5.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "팀 기여도 순위",
                style = Typography.displayLarge,
                fontSize = 35.sp,
                modifier = Modifier.padding(10.dp)
            )
        }
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 10.dp)
                .fillMaxWidth()
        ){
            items(items) { item ->
                RecordListItem()
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun Game01ResultScreenPreview(){
    MallangTheme {
        Game01ResultScreen()
    }
}
