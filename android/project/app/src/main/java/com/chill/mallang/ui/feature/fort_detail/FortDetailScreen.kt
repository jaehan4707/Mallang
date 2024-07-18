package com.chill.mallang.ui.feature.fort_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.R
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun FortDetailScreen(modifier: Modifier = Modifier){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        Header(fort_name = "찰밭공원")
        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ){
            MainBody()
            MyRecordBody()
            HistorySubBody()
        }
    }
}

@Composable
fun Header(modifier: Modifier = Modifier, fort_name: String){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray.copy(alpha = 0.4f))
            .padding(vertical = 7.dp, horizontal = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
            ,horizontalArrangement = Arrangement.spacedBy(4.dp)
            , verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_location)
                , contentDescription = "검정색 마커"
                , modifier = Modifier.height(height = 20.dp)
            )
            Text(text = fort_name, fontSize = 18.sp)
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_exit)
            , contentDescription = "종료"
            , modifier = Modifier.align(Alignment.TopEnd)
        )
    }
}

@Composable
fun MainBody(modifier: Modifier = Modifier){
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 10.dp)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(text = "[찰밭공원군주]", color = Color.Red)
            Image(
                painter = painterResource(id = R.mipmap.malang),
                contentDescription = "My Image"
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_master_tier),
                    contentDescription = "My Image",
                    modifier = Modifier.height(30.dp)
                )
                Text(text = "나갱갱", fontSize = 22.sp)
                Text(text = "98점", fontSize = 22.sp, color = Color.Red)
            }
        }
    }
}

@Composable
fun MyRecordBody(modifier: Modifier = Modifier){
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "나의 도전", fontWeight = FontWeight.SemiBold)
                Row(
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Text(text = "남은 기회: ", fontWeight = FontWeight.SemiBold)
                    Text(text = "${3}번", color = Color.Red, fontWeight = FontWeight.SemiBold)
                }
            }
            LazyColumn {
                item {
                    ListItemMyRecord()
                }
                item {
                    ListItemMyRecord()
                }
                item {
                    ListItemMyRecord()
                }
            }
        }
    }
}

@Composable
fun HistorySubBody(modifier: Modifier = Modifier){
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "도전 기록", fontWeight = FontWeight.SemiBold)
            }
            LazyColumn {
                item {
                    ListItemHistory()
                }
                item {
                    ListItemHistory()
                }
                item {
                    ListItemHistory()
                }
            }
        }
    }
}

@Composable
fun ListItemMyRecord(modifier: Modifier = Modifier){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .height(height = 65.dp)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .border(2.dp, Color.Black, shape = RoundedCornerShape(16.dp))
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
            , verticalAlignment = Alignment.CenterVertically
            , horizontalArrangement = Arrangement.spacedBy(20.dp)
        ){
            Text(text = "WIN", fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = Color.Blue)
            Text(text = "98점", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
            Text(text = "1등 달성!", fontSize = 12.sp, color = Color.Red, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun ListItemHistory(modifier: Modifier = Modifier){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .height(height = 65.dp)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .border(2.dp, Color.Black, shape = RoundedCornerShape(16.dp))
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
            , verticalAlignment = Alignment.CenterVertically
            , horizontalArrangement = Arrangement.spacedBy(20.dp)
        ){
            Text(text = "LOSE", fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = Color.Red)
            Text(text = "짜이한", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
            Text(text = "랑", fontSize = 16.sp, color = Color.Blue, fontWeight = FontWeight.SemiBold)
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun FortDetailScreenPreview() {
    MallangTheme {
        FortDetailScreen()
    }
}
