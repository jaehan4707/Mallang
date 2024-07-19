package com.chill.mallang.ui.feature.login

import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {
    private val _loginResult = MutableStateFlow<Result<GoogleSignInAccount>?>(null)
    val loginResult: StateFlow<Result<GoogleSignInAccount>?> = _loginResult

    private val auth = FirebaseAuth.getInstance()

    fun loginWithGoogle(account: GoogleSignInAccount?) {
        account?.let { googleAccount ->
            val credential = GoogleAuthProvider.getCredential(googleAccount.idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _loginResult.value = Result.success(googleAccount)
                    } else {
                        _loginResult.value =
                            Result.failure(task.exception ?: Exception("Firebase Auth Failed"))
                    }
                }
        } ?: run {
            _loginResult.value = Result.failure(Exception("Google Sign-In Failed"))
        }
    }
}