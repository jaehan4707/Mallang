package com.chill.mallang.ui.component.experiencebar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chill.mallang.R
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun ErrorDialog(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit = {},
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.img_lost_map),
            contentDescription = "lost",
        )
        Text(modifier = Modifier.padding(vertical = 16.dp), text = "데이터를 불러오는데 실패하였습니다.")
        Button(
            modifier = Modifier.padding(12.dp),
            colors =
                ButtonDefaults.buttonColors(
                    contentColor = White,
                    containerColor = Gray6,
                ),
            shape = RoundedCornerShape(10.dp),
            onClick = onRetry,
        ) {
            Text(text = "다시 시도")
        }
    }
}

@Preview
@Composable
fun ErrorDialogPreview() {
    MallangTheme {
        ErrorDialog()
    }
}
