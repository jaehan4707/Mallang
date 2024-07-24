package com.chill.mallang.ui.feature.nickname

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.chill.mallang.ui.util.ErrorMessage
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

    private fun generateErrorMsg(newNickname: String): String {
        return when {
            newNickname.isEmpty() -> ErrorMessage.EMPTY_MESSAGE
            newNickname.length < 2 -> ErrorMessage.TOO_SHORT
            newNickname.length > 10 -> ErrorMessage.TOO_LONG
            !nicknameRegex.matches(newNickname) -> ErrorMessage.INVALID_CHAR
            else -> ""
        }
    }
}