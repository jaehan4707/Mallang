package com.chill.mallang.ui.feature.login

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    private val _loginResult = MutableStateFlow<Result<GoogleSignInAccount>?>(null)
    val loginResult: StateFlow<Result<GoogleSignInAccount>?> = _loginResult

    fun loginWithGoogle(account: GoogleSignInAccount?) {
        account?.let { account ->
            _loginResult.value = Result.success(account)
        } ?: run {
            _loginResult.value = Result.failure(Exception("Google Sign-In Failed"))
        }
    }
}