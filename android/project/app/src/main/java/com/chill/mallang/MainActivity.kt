package com.chill.mallang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.chill.mallang.ui.MainScreen
import com.chill.mallang.ui.sound.SoundManager
import com.chill.mallang.ui.theme.MallangTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var soundManager: SoundManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MallangTheme {
                MainScreen()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        soundManager.pauseBackgroundMusic()
    }

    override fun onResume() {
        super.onResume()
        soundManager.resumeBackgroundMusic()
    }
}
