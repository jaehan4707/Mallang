package com.chill.mallang.ui.feature.game.game01.SubView.ResultScreen.Layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.chill.mallang.R
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography

@Composable
fun ResultTitleBody(
    isVictory: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = if(isVictory) stringResource(id = R.string.success_message) else stringResource(id = R.string.nice_try_message),
            style = Typography.displayLarge,
            fontSize = 40.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SuccessResultTitleBodyPreview() {
    MallangTheme {
        ResultTitleBody(
            isVictory = true,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NiceTryResultTitleBodyPreview() {
    MallangTheme {
        ResultTitleBody(
            isVictory = false,
        )
    }
}
