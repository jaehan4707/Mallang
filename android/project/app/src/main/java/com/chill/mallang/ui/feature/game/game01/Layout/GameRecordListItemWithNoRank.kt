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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.R
import com.chill.mallang.data.model.entity.GameUserRecord
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Sub3
import com.chill.mallang.ui.theme.SuperLightSkyBlue
import com.chill.mallang.ui.theme.Typography

@Composable
fun GameRecordListItemWithNoRank(
    userRecord: GameUserRecord,
    modifier: Modifier = Modifier,
    isUserRecord: Boolean = false,
    teamId: Long = 1L,
) {
    val borderThickness = if (isUserRecord) 2.dp else 1.dp
    val teamColor = if (teamId == 1L) Color.Red else Color.Blue
    val backgroundCcolor =
        if (isUserRecord) {
            if (teamId == 1L) {
                Sub3
            } else {
                SuperLightSkyBlue
            }
        } else {
            Color.Transparent
        }

    Box(
        contentAlignment = Alignment.Center,
        modifier =
            modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
                .height(height = 65.dp)
                .background(backgroundCcolor, shape = RoundedCornerShape(16.dp))
                .border(borderThickness, Color.Black, shape = RoundedCornerShape(16.dp)),
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
                text = "순위권 밖",
                fontSize = 24.sp,
                style = Typography.displayLarge,
                color = teamColor,
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

@Preview(showBackground = true)
@Composable
fun RedGameRecordListItemWithNoRankPreview() {
    MallangTheme {
        GameRecordListItemWithNoRank(
            userRecord = GameUserRecord(userName = "사람10", userScore = 100F),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RedUsergameRecordListItemWithNoRankPreview() {
    MallangTheme {
        GameRecordListItemWithNoRank(
            userRecord = GameUserRecord(userName = "사람10", userScore = 100F),
            isUserRecord = true,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BlueGameRecordListItemWithNoRankPreview() {
    MallangTheme {
        GameRecordListItemWithNoRank(
            userRecord = GameUserRecord(userName = "사람10", userScore = 100F),
            teamId = 2,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BlueUserGameRecordListItemWithNoRankPreview() {
    MallangTheme {
        GameRecordListItemWithNoRank(
            userRecord = GameUserRecord(userName = "사람10", userScore = 100F),
            teamId = 2,
            isUserRecord = true,
        )
    }
}
