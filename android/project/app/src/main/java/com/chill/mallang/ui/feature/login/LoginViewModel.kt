package com.chill.mallang.ui.feature.login

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chill.mallang.BuildConfig
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
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
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    private val _loginResult = MutableStateFlow<FirebaseUser?>(null)
    val loginResult: StateFlow<FirebaseUser?> = _loginResult

    // web client key
    private val key = BuildConfig.WEB_CLIENT_ID

    // firebase 인증 인스턴스
    private val auth = FirebaseAuth.getInstance()

    // Credential Manager 인스턴스
    private lateinit var credentialManager: CredentialManager

    // Credential Request
    private lateinit var request: GetCredentialRequest

    // Google Sign-In Client
    private lateinit var googleSignInClient: GoogleSignInClient

    // google sign in launcher
    private var googleSignInLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>? = null

    fun setGoogleSignInLauncher(launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
        googleSignInLauncher = launcher
    }

    // Credential Manager 초기화
    fun initCredentialManager(context: Context) {
        credentialManager = CredentialManager.create(context)

        // Google Sign-In 클라이언트 초기화
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(key)
            .requestEmail()
            .requestProfile()
            .build()
        googleSignInClient = GoogleSignIn.getClient(context, gso)
    }

    // CredentialRequest 생성
    fun initCredentialRequest() {
        request = getGoogleCredentialRequest(key)
    }

    // Credential Manager 생성 및 Request 받기
    fun getCredential(context: Context) {
        viewModelScope.launch {
            try {
                val credentialResponse = credentialManager.getCredential(context, request)
                handleSignInResult(credentialResponse)
            } catch (e: Exception) {
                Toast.makeText(context, "로그인 시작 실패: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fallbackToGoogleSignIn(googleSignInLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>?) {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher?.launch(signInIntent)
    }

    fun handleActivityResult(requestCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.let {
                    firebaseAuthWithGoogle(it.idToken!!)
                }
            } catch (e: ApiException) {
                Log.w("loginViewModel", "Google sign in failed", e)
            }
        }
    }

    // Credential Request 얻기
    private fun getGoogleCredentialRequest(
        clientId: String,
        nonce: String? = null
    ): GetCredentialRequest {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(clientId)
            .setFilterByAuthorizedAccounts(false)
            .setNonce(nonce)
            .build()

        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    // 구글 로그인 화면 띄우는 함수
    fun initializeLogin(
        context: Context,
        credentialLauncher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>
    ) {
        viewModelScope.launch {
            try {
                val intentSender = signInWithGoogle(context, request)
                if (intentSender != null) {
                    credentialLauncher.launch(IntentSenderRequest.Builder(intentSender).build())
                }
            } catch (e: Exception) {
                when (e) {
                    is NoCredentialException -> {
                        // Credential Manager 실패 시 Google Sign-In API로 폴백
                        fallbackToGoogleSignIn(googleSignInLauncher)
                    }
                    else -> {
                        Toast.makeText(context, "로그인 시작 실패: ${e.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    // Google 로그인 (토큰 인증)
    private suspend fun signInWithGoogle(
        context: Context,
        request: GetCredentialRequest
    ): IntentSender? {
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

    private fun handleSignInResult(credentialResponse: GetCredentialResponse) {
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
    private fun firebaseAuthWithGoogle(idToken: String) {
        viewModelScope.launch {
            try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                val authResult = auth.signInWithCredential(credential).await()
                val user = authResult.user

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

    companion object {
        const val RC_SIGN_IN = 9001
    }
}