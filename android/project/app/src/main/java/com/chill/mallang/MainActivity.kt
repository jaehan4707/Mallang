package com.chill.mallang

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import com.chill.mallang.ui.feature.login.LoginScreen
import com.chill.mallang.ui.feature.login.LoginViewModel
import com.chill.mallang.ui.navigation.MallangNavHost
import com.chill.mallang.ui.theme.MallangTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MallangTheme {
                MallangNavHost()
            }
        }
    }
}