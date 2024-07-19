package com.chill.mallang.ui.feature.login

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chill.mallang.BuildConfig
import com.chill.mallang.R
import com.chill.mallang.ui.theme.BackGround
import com.chill.mallang.ui.theme.Gray4
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.Typography
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginClick: () -> Unit
) {

    val context = LocalContext.current
    val webClientId = BuildConfig.WEB_CLIENT_ID

    val googleSignInClient = remember {
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .requestIdToken(webClientId)
                .build()
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                viewModel.loginWithGoogle(account)

                Log.d("nakyung", "email: " + account.email!!)
                Log.d("nakyung", "name: " + account.displayName!!)
                Log.d("nakyung", "profile: " + account.photoUrl.toString())

            } catch (e: ApiException) {
                viewModel.loginWithGoogle(null)
            }
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
                launcher.launch(googleSignInClient.signInIntent)
            }, text = "구글 계정으로 로그인하기")
            Spacer(modifier = Modifier.weight(0.7f))
        }
    }

    // 로그인 결과 처리
    val loginResult by viewModel.loginResult.collectAsState()
    LaunchedEffect(loginResult) {
        loginResult?.let { result ->
            if (result.isSuccess) {
                onLoginClick()
            } else {
                Toast.makeText(context, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
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