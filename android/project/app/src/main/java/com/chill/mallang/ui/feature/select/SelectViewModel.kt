package com.chill.mallang.ui.feature.select

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SelectViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _isSignUp = MutableStateFlow<Boolean>(false)
    val isSignUp = _isSignUp.asStateFlow()

    fun login(team: String?) {
        val userName = savedStateHandle.get<String>("userName")
        val userEmail = savedStateHandle.get<String>("userEmail")
        val userProfileImageUrl = savedStateHandle.get<String>("userProfileImageUrl")
        val userNickName = savedStateHandle.get<String>("userNickName")
        team?.let { //회원가입 로직 후 성공했다면,
            _isSignUp.update {
                true
            }
        }
    }
}