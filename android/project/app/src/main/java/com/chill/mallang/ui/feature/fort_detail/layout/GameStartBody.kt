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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.R
import com.chill.mallang.ui.feature.fort_detail.TryCountState
import com.chill.mallang.ui.feature.map.MapDistance.inArea
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography

@Composable
fun GameStartBody(
    modifier: Modifier = Modifier,
    distance: Int?,
    tryCountState: TryCountState,
    onStartGame: () -> Unit = {},
) {
    val enabled by remember {
        mutableStateOf(
                tryCountState is TryCountState.Success &&
                tryCountState.tryCount > 0,
        )
    }
    val inRange by remember {
        mutableStateOf(
            distance != null &&
                    distance < inArea
        )
    }

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Button(
            enabled = enabled && inRange,
            onClick = onStartGame,
            shape = RoundedCornerShape(10.dp),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White,
                ),
        ) {
            Text(
                text = if (inRange) stringResource(R.string.game_start) else stringResource(R.string.too_far),
                style = Typography.displayLarge,
                fontSize = 30.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            )
        }
        when (tryCountState) {
            is TryCountState.Success -> {
                Text(text = stringResource(R.string.remaining_chance, tryCountState.tryCount))
            }

            else -> {
                Text(text = stringResource(R.string.remaining_chance, 0))
            }
        }
    }
}

@Preview
@Composable
fun GameStartBodyPreview() {
    MallangTheme {
        GameStartBody(distance = 30, tryCountState = TryCountState.Success(3))
    }
}

@Preview
@Composable
fun GameStartBodyPreviewWithNoChance() {
    MallangTheme {
        GameStartBody(distance = 30, tryCountState = TryCountState.Success(0))
    }
}

@Preview
@Composable
fun GameStartBodyPreviewTooFar() {
    MallangTheme {
        GameStartBody(distance = 300, tryCountState = TryCountState.Success(3))
    }
}
