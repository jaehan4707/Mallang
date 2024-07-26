package com.chill.mallang.ui.feature.select

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chill.mallang.data.model.request.JoinRequest
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.remote.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Loading())
    val uiState = _uiState.asStateFlow()

    init {
        val state = uiState.value
        if (state is SignUpUiState.Loading) {
            with(savedStateHandle) {
                _uiState.update {
                    state.copy(
                        userEmail = get<String>("userEmail"),
                        userProfileImageUrl = get<String>("userProfileImageUrl"),
                        userNickName = get<String>("userNickName")
                    )
                }
            }

        }
    }


    fun join(team: String?) {
        val state = uiState.value
        viewModelScope.launch {
            if (state is SignUpUiState.Loading) {
                userRepository.join(
                    JoinRequest(
                        userEmail = state.userEmail ?: "",
                        userProfileImageUrl = state.userProfileImageUrl ?: "",
                        userNickName = state.userNickName ?: "",
                        team = team ?: ""
                    )
                ).collectLatest { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            _uiState.value = SignUpUiState.Success
                        }

                        is ApiResponse.Error -> {
                            _uiState.value =
                                SignUpUiState.Error(errorMessage = response.errorMessage)
                        }

                        is ApiResponse.Init -> {}
                    }
                }
            }
        }
    }
}