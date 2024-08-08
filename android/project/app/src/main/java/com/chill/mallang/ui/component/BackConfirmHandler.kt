package com.chill.mallang.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun BackConfirmHandler(
    isBackPressed: Boolean = true,
    onConfirm: Pair<String, () -> Unit>,
    onDismiss: Pair<String, (Boolean) -> Unit>,
    title: String = "",
    content: String = "",
) {
    val showDialog =
        remember {
            mutableStateOf(false)
        }
    LaunchedEffect(isBackPressed) {
        showDialog.value = isBackPressed
    }

    if (showDialog.value) {
        BackConfirmDialog(
            title = title,
            onConfirm = onConfirm,
            onDismiss = Pair(onDismiss.first) { onDismiss.second(false) },
            content = content,
        )
    }
}
