package com.chill.mallang.ui.feature.select

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chill.mallang.data.model.request.JoinRequest
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.remote.AreaRepository
import com.chill.mallang.data.repository.remote.FactionRepository
import com.chill.mallang.data.repository.remote.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectViewModel
    @Inject
    constructor(
        private val savedStateHandle: SavedStateHandle,
        private val userRepository: UserRepository,
        private val factionRepository: FactionRepository,
        private val areaRepository: AreaRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<SignUpUiState>(SignUpUiState())
        val uiState = _uiState.asStateFlow()
        private val _event = MutableSharedFlow<SignUiEvent>()
        val event = _event.asSharedFlow()

        init {
            getAreaStatus()
        }

        private fun getAreaStatus() {
            viewModelScope.launch {
                factionRepository.getFactionRatios().collectLatest { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            _uiState.update { state ->
                                state.copy(
                                    factionsStatus =
                                        response.body ?: persistentListOf(),
                                )
                            }
                        }

                        is ApiResponse.Error -> {
                            _event.emit(SignUiEvent.Error(response.errorMessage))
                        }

                        ApiResponse.Init -> {}
                    }
                }
            }
        }

        fun join(team: String?) {
            val userEmail = savedStateHandle.get<String>("userEmail") ?: ""
            val userProfileImageUrl = savedStateHandle.get<String>("userProfileImageUrl") ?: ""
            val userNickName = savedStateHandle.get<String>("userNickName") ?: ""
            viewModelScope.launch {
                userRepository
                    .join(
                        JoinRequest(
                            userEmail = userEmail,
                            userProfileImageUrl = userProfileImageUrl,
                            userNickName = userNickName,
                            team = team ?: "",
                        ),
                    ).collectLatest { response ->
                        when (response) {
                            is ApiResponse.Success -> {
                                _event.emit(SignUiEvent.SignUpSuccess)
                            }

                            is ApiResponse.Error -> {
                                _event.emit(SignUiEvent.Error(response.errorMessage))
                            }

                            is ApiResponse.Init -> {}
                        }
                    }
            }
        }
    }
