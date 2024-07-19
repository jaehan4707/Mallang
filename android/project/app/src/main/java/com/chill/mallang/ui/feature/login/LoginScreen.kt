package com.chill.mallang.ui.feature.login

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.hilt.navigation.compose.hiltViewModel
import com.chill.mallang.BuildConfig
import com.chill.mallang.R
import com.chill.mallang.ui.theme.BackGround
import com.chill.mallang.ui.theme.Gray4
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.Typography
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun LoginScreen(
    onLoginSuccess: (LoginUiState) -> Unit
) {
    val viewModel: LoginViewModel = hiltViewModel()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.initCredentialManager(context)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let {
                val credentialManager = CredentialManager.create(context)
                val request = viewModel.getGoogleCredentialRequest(BuildConfig.WEB_CLIENT_ID)
                scope.launch {
                    try {
                        val credentialResponse = credentialManager.getCredential(context, request)
                        viewModel.handleSignInResult(credentialResponse)
                    } catch (e: Exception) {
                        Toast.makeText(context, "로그인 실패: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    val loginResult by viewModel.loginResult.collectAsState()
    LaunchedEffect(loginResult) {
        loginResult?.let { result ->
            onLoginSuccess(
                LoginUiState(
                    userName = result.displayName,
                    userEmail = result.email,
                    userProfileImageUrl = URLEncoder.encode(
                        result.photoUrl.toString(),
                        StandardCharsets.UTF_8.toString()
                    )
                )
            )
        }
    }

    Surface(
        color = BackGround,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.1f))
            Image(
                painter = painterResource(id = R.drawable.ic_title),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.weight(0.4f))
            GoogleLoginButton(onClick = {
                scope.launch {
                    try {
                        val request =
                            viewModel.getGoogleCredentialRequest(BuildConfig.WEB_CLIENT_ID)
                        val intentSender =
                            viewModel.signInWithGoogle(context, request)
                        if (intentSender != null) {
                            launcher.launch(IntentSenderRequest.Builder(intentSender).build())
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, "로그인 시작 실패: ${e.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }, text = "구글 계정으로 로그인하기")
            Spacer(modifier = Modifier.weight(0.7f))
        }
    }
}

@Composable
fun GoogleLoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = Gray6,
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Gray4),
        modifier = modifier
            .fillMaxWidth(0.8f)
            .height(48.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = null,
                modifier = Modifier.width(28.dp)
            )
            Box(modifier = Modifier.width(10.dp))
            Text(
                text, style = Typography.displayLarge
            )
        }
    }
}