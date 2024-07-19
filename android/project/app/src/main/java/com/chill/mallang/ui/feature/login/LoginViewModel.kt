package com.chill.mallang.ui.feature.login

import android.content.Context
import android.content.IntentSender
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    private val _loginResult = MutableStateFlow<FirebaseUser?>(null)
    val loginResult: StateFlow<FirebaseUser?> = _loginResult

    // firebase 인증 인스턴스
    private val auth = FirebaseAuth.getInstance()

    // Credential Manager 인스턴스
    private lateinit var credentialManager: CredentialManager

    // Credential Manager 초기화
    fun initCredentialManager(context: Context) {
        credentialManager = CredentialManager.create(context)
    }

    // Credential Request 얻기
    fun getGoogleCredentialRequest(clientId: String, nonce: String? = null): GetCredentialRequest {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(clientId)
            .setNonce(nonce)
            .build()

        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    // Google 로그인
    suspend fun signInWithGoogle(context: Context, request: GetCredentialRequest): IntentSender? {
        return withContext(Dispatchers.IO) {
            try {
                val result = credentialManager.getCredential(context, request)
                handleSignInResult(result)
                null
            } catch (e: GetCredentialException) {
                when (val cause = e.cause) {
                    is ResolvableApiException -> cause.resolution.intentSender
                    else -> throw e
                }
            }
        }
    }

    fun handleSignInResult(credentialResponse: GetCredentialResponse) {
        viewModelScope.launch {
            try {
                when (val credential = credentialResponse.credential) {
                    is CustomCredential -> {
                        if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                            val googleIdTokenCredential =
                                GoogleIdTokenCredential.createFrom(credential.data)
                            firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
                        } else {
                            throw Exception("Unexpected credential type")
                        }
                    }

                    else -> throw Exception("Unexpected credential type")
                }
            } catch (e: Exception) {
                _loginResult.value = null
            }
        }
    }

    // Google ID 토큰을 사용해 Firebase 인증
    private suspend fun firebaseAuthWithGoogle(idToken: String) {
        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential).await()

            val user = auth.currentUser
            user?.let {
                Log.d("nakyung", user.email!!)
                Log.d("nakyung", user.photoUrl.toString())
            }

            _loginResult.value = user
        } catch (e: Exception) {
            _loginResult.value = null
        }
    }
}