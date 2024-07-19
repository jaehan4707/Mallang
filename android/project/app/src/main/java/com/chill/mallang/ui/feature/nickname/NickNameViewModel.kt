package com.chill.mallang.ui.feature.nickname

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NickNameViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        val userName = savedStateHandle.get<String>("userName")
        val userEmail = savedStateHandle.get<String>("userEmail")
        val userProfileImageUrl = savedStateHandle.get<String>("userProfileImageUrl")
        Log.d("jaehan","NickName : ${userName} ${userEmail} ${userProfileImageUrl}")
    }
}