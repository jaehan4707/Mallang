package com.chill.mallang.ui.feature.fort_detail.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.R
import com.chill.mallang.data.model.entity.TeamInfo
import com.chill.mallang.ui.feature.fort_detail.AreaDetailState
import com.chill.mallang.ui.theme.Typography


@Composable
fun DetailBody(
    occupationState: AreaDetailState,
    modifier: Modifier = Modifier,
) {
    when (occupationState) {
        is AreaDetailState.Loading -> {
            Surface(
                modifier = modifier.fillMaxSize(),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(48.dp))
                }
            }
        }

        is AreaDetailState.Error -> {
            Surface(
                modifier = modifier.fillMaxSize(),
            ) {
                Text(occupationState.errorMessage.getErrorMessage(LocalContext.current))
            }
        }

        is AreaDetailState.Success -> {
            Surface(
                modifier =
                modifier
                    .fillMaxSize()
                    .padding(vertical = 20.dp, horizontal = 10.dp),
            ) {
                Column {
                    FortDetailHeader(
                        fortName = occupationState.areaDetail.areaName,
                        modifier = Modifier.weight(1F),
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        modifier = Modifier.weight(6F),
                    ) {
                        TeamScoreAndTopUser(
                            teamInfo = occupationState.areaDetail.myTeamInfo,
                            modifier =
                            Modifier
                                .weight(1F)
                                .fillMaxHeight(),
                        )
                        Text("VS", fontSize = 40.sp)
                        TeamScoreAndTopUser(
                            teamInfo = occupationState.areaDetail.oppoTeamInfo,
                            modifier =
                            Modifier
                                .weight(1F)
                                .fillMaxHeight(),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FortDetailHeader(
    fortName: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
        modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.LightGray.copy(alpha = 0.4f))
            .padding(vertical = 7.dp, horizontal = 10.dp),
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = "",
                modifier = Modifier.height(height = 20.dp),
            )
            Text(text = fortName, fontSize = 18.sp)
        }
    }
}

@Composable
fun TeamScoreAndTopUser(
    teamInfo: TeamInfo,
    modifier: Modifier = Modifier,
) {
    val teamColor by remember(teamInfo.teamId) { mutableStateOf(if (teamInfo.teamId == 1) Color.Red else Color.Blue) }
    val teamTopUserTitle =
        if (teamInfo.teamId == 1) stringResource(R.string.team_mal_title) else stringResource(R.string.team_rang_title)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = stringResource(R.string.top_user_title, teamTopUserTitle), color = teamColor)
        Image(
            painter = painterResource(id = R.mipmap.malang),
            contentDescription = "",
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_master_tier),
                contentDescription = "",
                modifier = Modifier.height(30.dp),
            )
            if (teamInfo.topUser != null) {
                Text(
                    text = teamInfo.topUser.userName,
                    style = Typography.displayLarge,
                    fontSize = 28.sp,
                )
            }
        }
        Text(
            text = stringResource(R.string.team_score, teamInfo.teamPoint),
            fontSize = 30.sp,
            style = Typography.displayLarge,
            color = teamColor,
        )
    }
}