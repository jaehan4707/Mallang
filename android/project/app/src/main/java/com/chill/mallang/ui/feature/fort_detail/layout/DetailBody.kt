package com.chill.mallang.ui.feature.fort_detail.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.R
import com.chill.mallang.data.model.entity.AreaDetail
import com.chill.mallang.data.model.entity.TeamInfo
import com.chill.mallang.ui.component.LoadingBox
import com.chill.mallang.ui.feature.fort_detail.AreaDetailState
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography

@Composable
fun DetailBody(
    occupationState: AreaDetailState,
    modifier: Modifier = Modifier,
) {
    when (occupationState) {
        is AreaDetailState.Loading -> {
            LoadingBox()
        }

        is AreaDetailState.Error -> {
            Surface(
                modifier = modifier.fillMaxSize(),
            ) {
                Text(occupationState.errorMessage.getErrorMessage(LocalContext.current))
            }
        }

        is AreaDetailState.Success -> {
            val leftInfo by remember {
                mutableStateOf(
                    if (occupationState.areaDetail.myTeamInfo.teamId !=
                        1
                    ) {
                        occupationState.areaDetail.myTeamInfo
                    } else {
                        occupationState.areaDetail.oppoTeamInfo
                    },
                )
            }
            val rightInfo by remember {
                mutableStateOf(
                    if (occupationState.areaDetail.myTeamInfo.teamId !=
                        1
                    ) {
                        occupationState.areaDetail.oppoTeamInfo
                    } else {
                        occupationState.areaDetail.myTeamInfo
                    },
                )
            }

            Box(
                modifier =
                    modifier
                        .fillMaxSize(),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_vs_background_reverse),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    TeamScoreAndTopUser(
                        teamInfo = leftInfo,
                        modifier =
                            Modifier
                                .weight(1F)
                                .fillMaxHeight(),
                    )
                    TeamScoreAndTopUser(
                        teamInfo = rightInfo,
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

@Composable
fun FortDetailHeader(
    fortName: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
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
    val teamImage by remember {
        mutableIntStateOf(if (teamInfo.teamId == 1) R.drawable.img_mal_char_oppo else R.drawable.img_lang_char_oppo)
    }

    Column(
        modifier = modifier.padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Text(text = stringResource(R.string.top_user_title, teamTopUserTitle), color = teamColor)
        Image(
            modifier = Modifier.size(height = 160.dp, width = 120.dp),
            painter = painterResource(id = teamImage),
            contentDescription = "",
        )
        if (teamInfo.topUser != null) {
            Text(
                text = teamInfo.topUser.userName,
                style = Typography.displayLarge,
                fontSize = 28.sp,
            )
        }
        Text(
            text = stringResource(R.string.team_score, teamInfo.teamPoint),
            fontSize = 30.sp,
            style = Typography.displayLarge,
            color = teamColor,
        )
    }
}

@Preview
@Composable
fun DetailBodyPreview() {
    MallangTheme {
        DetailBody(occupationState = AreaDetailState.Loading)
    }
}

@Preview
@Composable
fun DetailBodyPreviewWithData() {
    MallangTheme {
        DetailBody(
            occupationState =
                AreaDetailState.Success(
                    AreaDetail(
                        areaName = "Name",
                        TeamInfo(1, 1, null),
                        TeamInfo(2, 1, null),
                    ),
                ),
        )
    }
}
