package com.chill.mallang.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.local.DataStoreRepository
import com.chill.mallang.data.repository.remote.UserRepository
import com.chill.mallang.ui.component.experiencebar.ExperienceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
        private val dataStoreRepository: DataStoreRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
        val uiState = _uiState.asStateFlow()
        private val _event = MutableSharedFlow<HomeUiEvent>()
        val event = _event.asSharedFlow()

        init {
            getUserInfo()
        }

        fun sendEvent(event: HomeUiEvent) {
            viewModelScope.launch {
                _event.emit(event)
            }
        }

        fun getUserInfo() {
            viewModelScope.launch {
                userRepository.getUserInfo().collectLatest { response ->
                    when (response) {
                        is ApiResponse.Error -> {
                            _event.emit(
                                HomeUiEvent.Error(
                                    errorCode = response.errorCode,
                                    errorMessage = response.errorMessage,
                                ),
                            )
                        }

                        is ApiResponse.Success -> {
                            val percentage =
                                response.body?.exp?.div(200 * (if (response.body.level == 0) 1 else response.body.level))
                                    ?: 0f

                            _uiState.value =
                                HomeUiState.LoadUserInfo(
                                    nickName = response.body?.nickName ?: "",
                                    factionId = response.body?.factionId ?: 0,
                                    experienceState =
                                        ExperienceState.Static(
                                            value = percentage,
                                            level = response.body?.level ?: 0,
                                        ),
                                )
                        }

                        ApiResponse.Init -> _uiState.value = HomeUiState.Loading
                    }
                }
            }
        }

        fun signOut() {
            viewModelScope.launch {
                userRepository.signOut().collectLatest { response ->
                    when (response) {
                        is ApiResponse.Error ->
                            _event.emit(
                                HomeUiEvent.Error(
                                    errorMessage = response.errorMessage,
                                    errorCode = response.errorCode,
                                ),
                            )

                        ApiResponse.Init -> {}

                        is ApiResponse.Success -> _event.emit(HomeUiEvent.SignOut("회원탈퇴 성공"))
                    }
                }
            }
        }

        fun logout() {
            viewModelScope.launch {
                dataStoreRepository.logout().collectLatest { response ->
                    when (response) {
                        is ApiResponse.Error ->
                            _event.emit(
                                HomeUiEvent.Error(
                                    errorCode = response.errorCode,
                                    errorMessage = response.errorMessage,
                                ),
                            )

                        ApiResponse.Init -> {}
                        is ApiResponse.Success -> _event.emit(HomeUiEvent.Logout("로그아웃 성공"))
                    }
                }
            }
        }
    }
