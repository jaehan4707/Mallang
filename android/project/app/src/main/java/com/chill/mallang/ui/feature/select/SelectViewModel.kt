package com.chill.mallang.ui.feature.select

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    //private val _isSignUp : Mu

    init {
    }

    fun login(team: String?) {
        val userName = savedStateHandle.get<String>("userName")
        val userEmail = savedStateHandle.get<String>("userEmail")
        val userProfileImageUrl = savedStateHandle.get<String>("userProfileImageUrl")
        val userNickName = savedStateHandle.get<String>("userNickName")
        team?.let {
            //repository -> login 요청
            // 결과 반환
        }
    }
}