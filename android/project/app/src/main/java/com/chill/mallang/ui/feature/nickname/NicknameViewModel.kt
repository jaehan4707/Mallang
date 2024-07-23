package com.chill.mallang.ui.feature.nickname

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NicknameViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val nicknameState = NicknameState()
}

class NicknameState() {
    private val nicknameRegex = "^[가-힣a-zA-Z]{2,10}$".toRegex() // 정규식

    var nickname by mutableStateOf("")
    var errorMessage by mutableStateOf("")

    fun updateNickname(newNickname: String) {
        nickname = newNickname
        errorMessage = generateErrorMsg(newNickname)
    }

    fun clearNickname() {
        nickname = ""
        errorMessage = ""
    }

    private fun generateErrorMsg(newNickname: String): String {
        return when {
            newNickname.isEmpty() -> "닉네임을 입력해 주세요"
            newNickname.length < 2 -> "닉네임은 2글자 이상이어야 합니다"
            newNickname.length > 10 -> "닉네임은 10글자 이하여야 합니다"
            !nicknameRegex.matches(newNickname) -> "닉네임은 한글 및 영문만 사용 가능합니다"
            else -> ""
        }
    }
}