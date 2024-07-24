package com.chill.mallang.ui.component

import androidx.compose.foundation.Image
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import com.chill.mallang.R
import com.chill.mallang.ui.theme.Typography

@Composable
fun BackConfirmDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Image(painter = painterResource(id = R.mipmap.malang), contentDescription = "") },
        title = { Text("말랑", style = Typography.headlineLarge) },
        text = { Text(text = "이전 화면으로 돌아가시겠습니까?", style = Typography.headlineMedium) },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    contentColor = White,
                    containerColor = Color.Black,
                ),
            ) {
                Text("네", style = Typography.displayMedium)
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
                Text("아니요", style = Typography.displayMedium)
            }
        }
    )
}