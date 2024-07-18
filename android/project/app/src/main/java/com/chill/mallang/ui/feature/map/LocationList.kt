package com.chill.mallang.ui.feature.map

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chill.mallang.R
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography

/**
 * 점령지 목록
 */
@Composable
fun LocationList(
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        items(10) {
            LocationListItem()
        }
    }
}

/**
 * 점령지 목록에 들어가는 각 점령지의 정보
 */
@Composable
fun LocationListItem(
    modifier: Modifier = Modifier
){
    Surface(
        modifier = modifier
            .border(
                border = BorderStroke(1.dp, Gray6),
                shape = RoundedCornerShape(16.dp)
            )
            .clip(
                shape = RoundedCornerShape(16.dp)
            )
    ){
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(4.dp)
                    .size(16.dp),
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = "location list item"
            )
            Text("찰밭공원", style = Typography.displayMedium)
            Spacer(modifier = Modifier.weight(1f))
            Text("10m", style = Typography.displayMedium)
        }
    }
}

@Preview
@Composable
private fun LocationListPreview(){
    MallangTheme {
        LocationList(modifier = Modifier.height(300.dp))
    }
}

@Preview(apiLevel = 34)
@Composable
private fun LocationListItemPreview(){
    MallangTheme {
        LocationListItem()
    }
}