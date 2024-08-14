package com.chill.mallang.ui.feature.game.game01.SubView.ResultScreen.Layout

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.R
import com.chill.mallang.data.model.entity.GameUserRecord
import com.chill.mallang.ui.feature.game.game01.Layout.GameRecordListItem
import com.chill.mallang.ui.feature.game.game01.Layout.GameRecordListItemWithNoRank
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LeaderBoardBody(
    userName: String,
    userScore: Float,
    userTeamId: Long,
    leaderList: List<GameUserRecord>,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val userPlace =
        leaderList.indexOfFirst { it.userName == userName && it.userScore == userScore } + 1

    val px_of_listItem = DpToPx()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            if (userPlace == 0){
                delay(2000L)
                listState.animateScrollBy(
                    value = px_of_listItem * leaderList.size,
                    animationSpec = tween(durationMillis = 1000, easing = LinearEasing),
                )
            } else if (userPlace < 3) {
                listState.scrollToItem(
                    index = leaderList.size - 1,
                )
                delay(2000L)
                listState.animateScrollBy(
                    value = px_of_listItem * leaderList.size * -1,
                    animationSpec = tween(durationMillis = 800, easing = LinearEasing),
                )
            } else {
                delay(2000L)
                listState.animateScrollBy(
                    value = px_of_listItem * (userPlace - 2),
                    animationSpec = tween(durationMillis = 800, easing = LinearEasing),
                )
            }
        }
    }

    Column(
        modifier =
        modifier
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .border(width = 2.dp, color = Gray6, shape = RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .height(300.dp),
    ) {
        Box(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(id = R.string.leader_board_title),
                style = Typography.displayLarge,
                fontSize = 30.sp,
                modifier = Modifier.padding(10.dp),
            )
        }
        LazyColumn(
            modifier =
            Modifier
                .padding(horizontal = 15.dp, vertical = 10.dp)
                .fillMaxWidth(),
            state = listState,
            userScrollEnabled = false,
        ) {
            itemsIndexed(leaderList) { index, userRecord ->
                if (index + 1 == userPlace) {
                    GameRecordListItem(
                        userPlace = index + 1,
                        userRecord = userRecord,
                        isUserRecord = true,
                        teamId = userTeamId,
                    )
                } else {
                    GameRecordListItem(
                        userPlace = index + 1,
                        userRecord = userRecord,
                        teamId = userTeamId,
                    )
                }
            }
            if (userPlace == 0) {
                item {
                    GameRecordListItemWithNoRank(
                        userRecord = GameUserRecord(userName = userName, userScore = userScore),
                        teamId = userTeamId,
                        isUserRecord = true
                    )
                }
            }
        }
    }
}

@Composable
fun DpToPx(): Float {
    val density = LocalDensity.current
    val dpValue = 76.dp
    val pxValue = with(density) { dpValue.toPx() }

    return pxValue
}

@Preview(showBackground = true)
@Composable
fun RedLeaderBoardBodyPreview() {
    MallangTheme {
        LeaderBoardBody(
            userName = "사람01",
            userScore = 100F,
            leaderList =
                listOf(
                    GameUserRecord("사람01", 100F),
                    GameUserRecord("사람02", 90F),
                    GameUserRecord("사람03", 80F),
                    GameUserRecord("사람04", 70F),
                    GameUserRecord("사람05", 60F),
                    GameUserRecord("사람06", 50F),
                    GameUserRecord("사람07", 40F),
                    GameUserRecord("사람08", 30F),
                    GameUserRecord("사람09", 20F),
                ),
            userTeamId = 1L,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BlueLeaderBoardBodyPreview() {
    MallangTheme {
        LeaderBoardBody(
            userName = "사람01",
            userScore = 100F,
            leaderList =
                listOf(
                    GameUserRecord("사람01", 100F),
                    GameUserRecord("사람02", 90F),
                    GameUserRecord("사람03", 80F),
                    GameUserRecord("사람04", 70F),
                    GameUserRecord("사람05", 60F),
                    GameUserRecord("사람06", 50F),
                    GameUserRecord("사람07", 40F),
                    GameUserRecord("사람08", 30F),
                    GameUserRecord("사람09", 20F),
                ),
            userTeamId = 2L,
        )
    }
}
