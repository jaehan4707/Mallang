package com.chill.mallang.ui.feature.login

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chill.mallang.BuildConfig
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.local.DataStoreRepository
import com.chill.mallang.data.repository.remote.FirebaseRepository
import com.chill.mallang.data.repository.remote.UserRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val firebaseRepository: FirebaseRepository,
        private val dataStoreRepository: DataStoreRepository,
        private val userRepository: UserRepository,
    ) : ViewModel() {
        private val _loginUiState = MutableStateFlow<LoginUiState>(LoginUiState.Loading)
        val loginUiState = _loginUiState.asStateFlow()
        private val _uiEvent = MutableSharedFlow<LoginUiEvent>()
        val uiEvent = _uiEvent.asSharedFlow()

        // firebase 인증 인스턴스
        private var auth: FirebaseAuth? = null

        // Credential Manager 인스턴스
        private lateinit var credentialManager: CredentialManager

        // Credential Request
        private lateinit var request: GetCredentialRequest

        // Google Sign-In Client
        private lateinit var googleSignInClient: GoogleSignInClient

        private var googleSignInLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>? = null

        init {
            loadUserInfo()
            getFirebaseAuthInstance()
        }

        private fun loadUserInfo() {
            viewModelScope.launch {
                dataStoreRepository.getUserEmail().collectLatest { email ->
                    email?.let {
                        userRepository.checkUserEmail(it).collectLatest { response ->
                            when (response) {
                                is ApiResponse.Error -> {
                                    when (response.errorCode) {
                                        409 -> _uiEvent.emit(LoginUiEvent.AuthLogin)
                                        else -> _loginUiState.value = LoginUiState.Loading
                                    }
                                }

                                else -> _loginUiState.value = LoginUiState.Loading
                            }
                        }
                    }
                }
            }
        }

        private fun getFirebaseAuthInstance() {
            auth = firebaseRepository.getFirebaseInstance()
        }

        fun setGoogleSignInLauncher(launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
            googleSignInLauncher = launcher
        }

        // Credential Manager 초기화
        fun initCredentialManager(context: Context) {
            credentialManager = CredentialManager.create(context)

            // Google Sign-In 클라이언트 초기화
            val gso =
                GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(BuildConfig.WEB_CLIENT_ID)
                    .requestEmail()
                    .requestProfile()
                    .build()
            googleSignInClient = GoogleSignIn.getClient(context, gso)
        }

        // CredentialRequest 생성
        fun initCredentialRequest() {
            request = getGoogleCredentialRequest(BuildConfig.WEB_CLIENT_ID)
        }

        // Credential Manager 생성 및 Request 받기
        fun getCredential(context: Context) {
            viewModelScope.launch {
                try {
                    val credentialResponse = credentialManager.getCredential(context, request)
                    handleSignInResult(credentialResponse)
                } catch (e: Exception) {
                    _loginUiState.value = LoginUiState.Error(errorMessage = e.message ?: "")
                }
            }
        }

        private fun fallbackToGoogleSignIn(googleSignInLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>?) {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher?.launch(signInIntent)
        }

        fun handleActivityResult(
            requestCode: Int,
            data: Intent?,
        ) {
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
            nonce: String? = null,
        ): GetCredentialRequest {
            val googleIdOption =
                GetGoogleIdOption
                    .Builder()
                    .setServerClientId(clientId)
                    .setFilterByAuthorizedAccounts(false)
                    .setNonce(nonce)
                    .build()

            return GetCredentialRequest
                .Builder()
                .addCredentialOption(googleIdOption)
                .build()
        }

        // 구글 로그인 화면 띄우는 함수
        fun initializeLogin(
            context: Context,
            credentialLauncher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
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

                        else ->
                            _loginUiState.value =
                                LoginUiState.Error(errorMessage = e.message ?: "")
                    }
                }
            }
        }

        // Google 로그인 (토큰 인증)
        private suspend fun signInWithGoogle(
            context: Context,
            request: GetCredentialRequest,
        ): IntentSender? =
            withContext(Dispatchers.IO) {
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
                    _loginUiState.value =
                        LoginUiState.Error(
                            errorMessage = e.message ?: "",
                        )
                }
            }
        }

        // Google ID 토큰을 사용해 Firebase 인증
        private fun firebaseAuthWithGoogle(idToken: String) {
            viewModelScope.launch(Dispatchers.IO) {
                firebaseRepository.firebaseAuthWithGoogle(idToken).collectLatest { authUser ->
                    authUser?.let { user ->
                        login(idToken, user.email ?: "", user.photoUrl.toString())
                    }
                }
            }
        }

        private fun login(
            idToken: String,
            userEmail: String,
            profileImageUrl: String,
        ) {
            viewModelScope.launch {
                userRepository.login(idToken, userEmail).collectLatest { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            when (response.body ?: false) {
                                true -> {
                                    _uiEvent.emit(LoginUiEvent.AuthLogin)
                                }

                                false -> {
                                    _loginUiState.value =
                                        LoginUiState.Success(
                                            userEmail = userEmail,
                                            userProfileImageUrl = profileImageUrl,
                                        )
                                }
                            }
                        }

                        is ApiResponse.Error -> {
                            _loginUiState.value =
                                LoginUiState.Error(
                                    errorMessage = response.errorMessage,
                                )
                        }

                        ApiResponse.Init -> {}
                    }
                }
            }
        }

        fun resetUi() {
            _loginUiState.value = LoginUiState.Loading
        }

        companion object {
            const val RC_SIGN_IN = 9001
        }
    }
