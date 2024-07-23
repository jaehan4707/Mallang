package com.chill.mallang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.ui.Modifier
import com.chill.mallang.ui.navigation.MallangNavHost
import com.chill.mallang.ui.theme.MallangTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val systemPaddingValue = WindowInsets.systemBars.asPaddingValues()

            MallangTheme {
                MallangNavHost(Modifier.padding(systemPaddingValue))
            }
        }
    }
}