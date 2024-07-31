package com.chill.mallang.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.remote.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
        val uiState = _uiState.asStateFlow()

        init {
            getUserInfo()
        }

        private fun getUserInfo() {
            viewModelScope.launch {
                userRepository.getUserInfo().collectLatest { response ->
                    _uiState.value =
                        when (response) {
                            is ApiResponse.Error -> {
                                HomeUiState.Error(
                                    errorCode = response.errorCode,
                                    errorMessage = response.errorMessage,
                                )
                            }

                            is ApiResponse.Success -> {
                                HomeUiState.LoadUserInfo(
                                    userNickName = response.data?.nickName ?: "",
                                    userFaction = response.data?.faction ?: "",
                                )
                            }

                            ApiResponse.Init -> HomeUiState.Loading
                        }
                }
            }
        }
    }
