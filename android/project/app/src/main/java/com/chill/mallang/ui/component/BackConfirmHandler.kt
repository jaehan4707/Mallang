package com.chill.mallang.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.chill.mallang.R

@Composable
fun BackConfirmHandler(
    isBackPressed: Boolean = true,
    onConfirmMessage: String = stringResource(id = R.string.positive_button_message),
    onDismissMessage: String = stringResource(id = R.string.nagative_button_message),
    onConfirm: () -> Unit = {},
    onDismiss: (Boolean) -> Unit = {},
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
            onConfirmMessage = onConfirmMessage,
            onDismiss = { onDismiss(false) },
            onDismissMessage = onDismissMessage,
            content = content,
        )
    }
}
