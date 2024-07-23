package com.chill.mallang.ui.feature.nickname

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NicknameViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val nicknameRegex = "^[가-힣a-zA-Z]{2,10}$".toRegex() // 정규식

    private val _nicknameState = MutableStateFlow(NicknameState())
    val nicknameState: StateFlow<NicknameState> = _nicknameState.asStateFlow()

    fun updateNickname(newNickname: String) {
        _nicknameState.update { currentState ->
            currentState.copy(
                nickname = newNickname,
                errorMessage = generateErrorMsg(newNickname)
            )
        }

        Log.d("nakyung", "viewmodel: " + nicknameState.value.nickname)
    }

    private fun generateErrorMsg(newNickname: String): String? {
        return when {
            newNickname.isEmpty() -> "닉네임을 입력해 주세요"
            newNickname.length < 2 -> "닉네임은 2글자 이상이어야 합니다"
            newNickname.length > 10 -> "닉네임은 10글자 이하여야 합니다"
            !nicknameRegex.matches(newNickname) -> "닉네임은 한글 및 영문만 사용 가능합니다"
            else -> null
        }
    }

    fun clearText() {
        _nicknameState.update { nicknameState ->
            nicknameState.copy(
                nickname = "",
                errorMessage = null
            )
        }
        Log.d("nakyung", "viewModel: " + nicknameState.value)
    }
}

data class NicknameState(
    val nickname: String = "",
    val errorMessage: String? = null
)