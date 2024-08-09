package com.chill.mallang.ui.feature.fort_detail.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.R
import com.chill.mallang.ui.theme.Typography


@Composable
fun GameStartBody(
    navigateToGame: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier =
        modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Button(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 100.dp),
            onClick = {
                navigateToGame()
            },
            shape = RoundedCornerShape(10.dp),
            colors =
            ButtonDefaults.buttonColors(
                Color.Black,
                Color.White,
            ),
        ) {
            Text(
                stringResource(R.string.game_start),
                style = Typography.displayLarge,
                fontSize = 30.sp,
            )
        }
        Text(text = stringResource(R.string.remaining_chance, 3))
    }
}