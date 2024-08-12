package com.chill.mallang.ui.feature.fort_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chill.mallang.data.model.entity.AreaDetail
import com.chill.mallang.data.model.entity.MyRecord
import com.chill.mallang.data.model.entity.TeamInfo
import com.chill.mallang.data.model.entity.TeamRecords
import com.chill.mallang.data.model.entity.UserInfo
import com.chill.mallang.data.model.entity.UserRecord
import com.chill.mallang.ui.feature.fort_detail.layout.DetailBody
import com.chill.mallang.ui.feature.fort_detail.layout.FortDetailHeader
import com.chill.mallang.ui.feature.fort_detail.layout.GameStartBody
import com.chill.mallang.ui.feature.fort_detail.layout.TeamRecordBody
import com.chill.mallang.ui.feature.topbar.TopbarHandler
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun FortDetailScreen(
    modifier: Modifier = Modifier,
    areaId: Long?,
    onStartGame: () -> Unit = {},
) {
    val viewModel: FortDetailViewModel = hiltViewModel()
    val occupationState by viewModel.areaDetailStateFlow.collectAsStateWithLifecycle()
    val teamLeadersState by viewModel.teamRecordStateFlow.collectAsStateWithLifecycle()
    val tryCountState by viewModel.tryCountStateFlow.collectAsStateWithLifecycle()

    var isLoaded by remember{ mutableStateOf(false) }
    
    DisposableEffect(isLoaded) {
        if(!isLoaded){
            if (areaId == null) {
                viewModel.invalidateData()
            } else {
                viewModel.loadOccupationState(areaId)
                viewModel.loadTeamLeadersState(areaId)
                viewModel.loadTryCount()
            }
            isLoaded = true
        }

        onDispose {
            isLoaded = false
        }
    }

    TopbarHandler(
        key = isLoaded,
        isVisible = true,
        titleContent = if(occupationState is AreaDetailState.Success){
            {FortDetailHeader(fortName = (occupationState as AreaDetailState.Success).areaDetail.areaName)}
        } else null,
    )

    AreaDetailContent(
        modifier = modifier,
        areaDetailState = occupationState,
        teamRecordState = teamLeadersState,
        onStartGame = onStartGame,
        tryCountState = tryCountState,
    )
}

@Composable
fun AreaDetailContent(
    modifier: Modifier = Modifier,
    areaDetailState: AreaDetailState,
    teamRecordState: TeamRecordState,
    tryCountState: TryCountState,
    onStartGame: () -> Unit = {},
) {
    Column(
        modifier =
            modifier
                .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        DetailBody(
            occupationState = areaDetailState,
            modifier = Modifier.weight(7F),
        )
        GameStartBody(
            modifier = Modifier.weight(2F),
            onStartGame = onStartGame,
            tryCountState = tryCountState,
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
            tryCountState = TryCountState.Loading,
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
                        MyRecord(1, 1, 1),
                        listOf(UserRecord(1, 1, 1)),
                        listOf(UserRecord(1, 1, 1)),
                    ),
                ),
            tryCountState = TryCountState.Success(3),
        )
    }
}
