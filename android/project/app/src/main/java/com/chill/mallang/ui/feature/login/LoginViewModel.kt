package com.chill.mallang.ui.feature.login

import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel: ViewModel() {
    private val _loginResult = MutableStateFlow<Result<GoogleSignInAccount>?>(null)
    val loginResult: StateFlow<Result<GoogleSignInAccount>?> = _loginResult

    fun loginWithGoogle(account: GoogleSignInAccount?) {
        account?.let {
            _loginResult.value = Result.success(it)
        } ?: run {
            _loginResult.value = Result.failure(Exception("Google Sign-In Failed"))
        }
    }
}