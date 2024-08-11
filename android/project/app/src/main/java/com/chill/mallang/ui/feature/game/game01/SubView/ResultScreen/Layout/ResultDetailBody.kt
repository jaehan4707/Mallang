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
fun ResultDetailBody(
    myTeamScore: Float = 0.0F,
    oppoTeamScore: Float = 0.0F,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text =
                if (myTeamScore > oppoTeamScore) {
                    stringResource(
                        id = R.string.win_message_format,
                        (myTeamScore - oppoTeamScore).toInt(),
                    )
                } else if (myTeamScore < oppoTeamScore) {
                    stringResource(
                        id = R.string.lose_message_format,
                        (oppoTeamScore - myTeamScore).toInt(),
                    )
                } else {
                    stringResource(id = R.string.draw_message_format)
                },
            style = Typography.bodySmall,
            fontSize = 20.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WinResultDetailBodyPreview() {
    MallangTheme {
        ResultDetailBody(
            myTeamScore = 100F,
            oppoTeamScore = 0F
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoseResultDetailBodyPreview() {
    MallangTheme {
        ResultDetailBody(
            myTeamScore = 0F,
            oppoTeamScore = 100F
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DrawResultDetailBodyPreview() {
    MallangTheme {
        ResultDetailBody(
            myTeamScore = 0F,
            oppoTeamScore = 0F
        )
    }
}
