package com.chill.mallang.ui.feature.map.layout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.R
import com.chill.mallang.data.model.Area
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography

/**
 * @param distance 사용자 현위치로부터의 거리 (m)
 */
@Composable
fun AreaInfoBar(
    modifier: Modifier = Modifier,
    area: Area,
    distance: Int?,
    onShowDetail: (Area) -> Unit = {},
){
    Surface(
        modifier = modifier
            .background(Color.White)
            .border(
                border = BorderStroke(2.dp, Gray6),
                shape = RoundedCornerShape(8.dp),
            )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_location), contentDescription = null)
            Text(text = area.areaName, style = Typography.bodyLarge)
            Text(text = if(distance==null) "" else "${distance}m", style = Typography.labelSmall)
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier.padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonColors(
                    contentColor = Gray6,
                    containerColor = Gray6,
                    disabledContentColor = Gray6,
                    disabledContainerColor = Gray6
                ),
                onClick = { onShowDetail(area) }
            ) {
                Text("보기", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@Preview
@Composable
fun LocationInfoBarPreview(){
    MallangTheme {
        AreaInfoBar(area = Area(1, "찰밭공원", 0.0, 0.0), distance = 10)
    }
}