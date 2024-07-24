package com.chill.mallang.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.chill.mallang.R
import com.chill.mallang.ui.theme.Typography

@Composable
fun BackConfirmDialog(
    content: String = "",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Image(painter = painterResource(id = R.mipmap.malang), contentDescription = "") },
        title = { Text(stringResource(R.string.mallang), style = Typography.headlineLarge) },
        text = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = content,
                style = Typography.headlineMedium,
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    contentColor = White,
                    containerColor = Color.Black,
                ),
            ) {
                Text(stringResource(R.string.positive_button_message), style = Typography.displayMedium)
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    contentColor = White,
                    containerColor = Color.Black,
                ),
            ) {
                Text(stringResource(R.string.nagative_button_message), style = Typography.displayMedium)
            }
        }
    )
}