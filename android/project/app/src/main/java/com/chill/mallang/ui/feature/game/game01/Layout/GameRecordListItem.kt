package com.chill.mallang.ui.feature.game.game01.Layout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.R
import com.chill.mallang.data.model.entity.GameUserRecord
import com.chill.mallang.ui.theme.Typography

@Composable
fun GameRecordListItem(
    userRecord: GameUserRecord,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
        modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .height(height = 65.dp)
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .border(1.dp, Color.Black, shape = RoundedCornerShape(16.dp)),
    ) {
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Text(
                text = stringResource(R.string.user_record_rank, userRecord.userPlace),
                fontSize = 24.sp,
                style = Typography.displayLarge,
                color = Color.Red,
            )
            Text(
                text = userRecord.userName,
                fontSize = 22.sp,
                style = Typography.displayLarge,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = stringResource(R.string.user_record_score, userRecord.userScore.toInt()),
                fontSize = 22.sp,
                style = Typography.displayLarge,
            )
        }
    }
}