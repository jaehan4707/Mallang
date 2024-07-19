package com.chill.mallang

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.chill.mallang.ui.feature.login.LoginScreen
import com.chill.mallang.ui.feature.login.LoginViewModel
import com.chill.mallang.ui.theme.MallangTheme

class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MallangTheme {
                LoginScreen(
                    viewModel = loginViewModel,
                    onLoginClick = {
                        // 로그인 성공 후 처리
                        Toast.makeText(applicationContext, "로그인 성공", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}