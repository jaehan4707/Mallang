package com.chill.mallang.ui.feature.nickname

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.remote.UserRepository
import com.chill.mallang.ui.util.ErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NicknameViewModel
    @Inject
    constructor(
        private val savedStateHandle: SavedStateHandle,
        private val userRepository: UserRepository,
    ) : ViewModel() {
        val nicknameState = NicknameState()
        private val _uiState = MutableStateFlow<NickNameUiState>(NickNameUiState.Init)
        val uiState = _uiState.asStateFlow()

        fun checkNickName() {
            viewModelScope.launch {
                userRepository
                    .checkNickName(nickName = nicknameState.nickname)
                    .collectLatest { response ->
                        when (response) {
                            is ApiResponse.Success -> {
                                _uiState.value =
                                    NickNameUiState.Success(nickName = nicknameState.nickname)
                            }

                            is ApiResponse.Error -> {
                                nicknameState.updateErrorMessage(ErrorMessage.DUPLICATED_NICKNAME)
                                _uiState.value =
                                    NickNameUiState.Error(errorMessage = nicknameState.errorMessage)
                            }

                            ApiResponse.Init -> {}
                        }
                    }
            }
        }
    }

class NicknameState {
    private val nicknameRegex = "^[가-힣a-zA-Z]{2,10}$".toRegex() // 정규식

    var nickname by mutableStateOf("")
        private set
    var errorMessage by mutableStateOf("")
        private set

    fun updateNickname(newNickname: String) {
        nickname = newNickname
        errorMessage = generateErrorMsg(newNickname)
    }

    fun clearNickname() {
        nickname = ""
        errorMessage = ""
    }

    fun updateErrorMessage(errorMessage: String) {
        this.errorMessage = errorMessage
    }

    private fun generateErrorMsg(newNickname: String): String =
        when {
            newNickname.isEmpty() -> ErrorMessage.EMPTY_MESSAGE
            newNickname.length < 2 -> ErrorMessage.TOO_SHORT
            newNickname.length > 10 -> ErrorMessage.TOO_LONG
            !nicknameRegex.matches(newNickname) -> ErrorMessage.INVALID_CHAR
            else -> ""
        }
}
