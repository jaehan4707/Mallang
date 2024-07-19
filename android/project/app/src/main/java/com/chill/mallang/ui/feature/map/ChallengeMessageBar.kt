package com.chill.mallang.ui.feature.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chill.mallang.R
import com.chill.mallang.ui.theme.BackGround
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.Typography


/**
 * 점령지 가까이 도착했을 때 나올 도전하기 버튼
 */
@Composable
fun ChallengeMessageBar(
    modifier: Modifier = Modifier
){
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp)),
        color = Gray6
    ) {
        Row(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = "location icon",
                tint = BackGround
            )
            Text("찰밭공원 도전하기", style = Typography.titleSmall, color = BackGround )
        }
    }
}