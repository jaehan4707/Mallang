package com.chill.mallang.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun ReloadEffect(
    onLoad: () -> Unit = {},
    onUnload: () -> Unit = {},
) {
    var isLoaded by remember { mutableStateOf(false) }

    DisposableEffect(isLoaded) {
        if (!isLoaded) {
            onLoad()
            isLoaded = true
        }
        onDispose {
            onUnload()
            isLoaded = false
        }
    }
}
