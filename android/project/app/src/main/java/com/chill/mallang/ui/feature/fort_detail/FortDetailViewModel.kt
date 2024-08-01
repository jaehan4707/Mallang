package com.chill.mallang.ui.feature.fort_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FortDetailViewModel @Inject constructor(
) : ViewModel() {
    private val _occupationState = MutableStateFlow<OccupationState>(OccupationState.Loading)
    val occupationState = _occupationState.asStateFlow()

    private val _teamLeadersState = MutableStateFlow<TeamLeadersState>(TeamLeadersState.Loading)
    val teamLeadersState = _teamLeadersState.asStateFlow()

    fun loadOccupationState() {
        viewModelScope.launch {
            // TODO : load from api
            _occupationState.emit(testOccupationState)
        }
    }

    fun loadTeamLeadersState() {
        viewModelScope.launch {
            // TODO : load from api
            _teamLeadersState.emit(testLeadersState)
        }
    }

    companion object {
        val testOccupationState: OccupationState =
            OccupationState.Success(
                areaName = "구미 캠퍼스",
                myTeamInfo = TeamInfo(1, 3990, UserInfo(1, "말대표", 1)),
                oppoTeamInfo = TeamInfo(2, 2990, UserInfo(2, "랑대표", 1)),
            )
        val testLeadersState: TeamLeadersState =
            TeamLeadersState.Success(
                userRecord = UserRecord(1, 1, "말대표", 100, 300),
                myTeamRecords =
                    listOf(
                        UserRecord(1, 1, "말대표", 100, 300),
                        UserRecord(2, 3, "말부하", 97, 300),
                    ),
                oppoTeamRecords =
                    listOf(
                        UserRecord(1, 4, "랑대표", 100, 300),
                        UserRecord(2, 5, "랑부하", 97, 300),
                    ),
            )
    }
}
