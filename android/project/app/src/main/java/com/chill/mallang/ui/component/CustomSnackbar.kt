package com.chill.mallang.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.chill.mallang.ui.theme.Typography

@Composable
fun CustomSnackBar(
    snackBarData: SnackbarData,
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    textColor: Color
) {
    Box(
        modifier =
            modifier
                .fillMaxSize(),
    ) {
        Surface(
            modifier =
                modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1f)
                    .align(Alignment.TopCenter)
                    .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            color = backgroundColor,
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = snackBarData.visuals.message,
                    style = Typography.displayLarge,
                    textAlign = TextAlign.Center,
                    color = textColor,
                )
            }
        }
    }
}
