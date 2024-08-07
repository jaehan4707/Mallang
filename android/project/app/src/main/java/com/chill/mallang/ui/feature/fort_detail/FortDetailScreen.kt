package com.chill.mallang.ui.feature.fort_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chill.mallang.data.model.entity.AreaDetail
import com.chill.mallang.data.model.entity.TeamInfo
import com.chill.mallang.data.model.entity.TeamRecords
import com.chill.mallang.data.model.entity.UserInfo
import com.chill.mallang.data.model.entity.UserRecord
import com.chill.mallang.ui.feature.fort_detail.layout.DetailBody
import com.chill.mallang.ui.feature.fort_detail.layout.GameStartBody
import com.chill.mallang.ui.feature.fort_detail.layout.TeamRecordBody
import com.chill.mallang.ui.feature.topbar.TopbarHandler
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun FortDetailScreen(
    modifier: Modifier = Modifier,
    areaId: Int?,
    userId: Int?,
    teamId: Int?,
) {
    val viewModel: FortDetailViewModel = hiltViewModel()
    val occupationState by viewModel.areaDetailStateFlow.collectAsStateWithLifecycle()
    val teamLeadersState by viewModel.teamRecordStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (areaId == null || userId == null || teamId == null) {
            viewModel.invalidateData()
        } else {
            viewModel.loadOccupationState(areaId, teamId)
            viewModel.loadTeamLeadersState(areaId, userId)
        }
    }

    TopbarHandler(isVisible = true)

    AreaDetailContent(
        modifier = modifier,
        areaDetailState = occupationState,
        teamRecordState = teamLeadersState,
    )
}

@Composable
fun AreaDetailContent(
    modifier: Modifier = Modifier,
    areaDetailState: AreaDetailState,
    teamRecordState: TeamRecordState,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize(),
    ) {
        DetailBody(
            occupationState = areaDetailState,
            modifier = Modifier.weight(7F),
        )
        GameStartBody(
            modifier = Modifier.weight(2F),
        )
        TeamRecordBody(
            teamRecordState = teamRecordState,
            modifier = Modifier.weight(7F),
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun FortDetailScreenPreview() {
    MallangTheme {
        AreaDetailContent(
            modifier = Modifier,
            areaDetailState = AreaDetailState.Loading,
            teamRecordState = TeamRecordState.Loading,
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun FortDetailScreenPreviewWithData() {
    MallangTheme {
        AreaDetailContent(
            modifier = Modifier,
            areaDetailState =
                AreaDetailState.Success(
                    AreaDetail(
                        areaName = "Name",
                        TeamInfo(1, 1, UserInfo(1, "Name")),
                        TeamInfo(1, 1, UserInfo(1, "Name")),
                    ),
                ),
            teamRecordState =
                TeamRecordState.Success(
                    TeamRecords(
                        UserRecord(1, 1, 1),
                        listOf(UserRecord(1, 1, 1)),
                        listOf(UserRecord(1, 1, 1)),
                    ),
                ),
        )
    }
}
