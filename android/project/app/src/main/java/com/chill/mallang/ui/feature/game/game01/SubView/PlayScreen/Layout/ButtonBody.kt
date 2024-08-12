package com.chill.mallang.ui.feature.game.game01.SubView.PlayScreen.Layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chill.mallang.R
import com.chill.mallang.ui.component.LongBlackButtonWithTimer
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun ButtonBody(
    remaingTime: Int,
    timeLimit: Int,
    submitUserAnswer: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ) {
        LongBlackButtonWithTimer(
            onClick = { submitUserAnswer() },
            buttonText = stringResource(id = R.string.submit_message),
            remainingTime = remaingTime,
            timeLimit = timeLimit,
            modifier = modifier.padding(horizontal = 20.dp),
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ButtonBodyPreview() {
    MallangTheme {
        ButtonBody(remaingTime = 100, timeLimit = 100, submitUserAnswer = {})
    }
}
