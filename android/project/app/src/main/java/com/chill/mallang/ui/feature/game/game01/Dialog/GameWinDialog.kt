package com.chill.mallang.ui.feature.game.game01.Dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.chill.mallang.R
import com.chill.mallang.ui.component.LongBlackButton
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameWinDialog(
    teamId: Long,
    onDismiss: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxWidth(0.95f),
        properties = DialogProperties(usePlatformDefaultWidth = false),
        content = {
            Surface(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.50f)
                        .padding(10.dp),
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(width = 2.dp, color = Gray6),
                color = Color.White,
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                        modifier = modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 90.dp),
                    ) {
                        Text(
                            text = stringResource(id = R.string.game_win_message),
                            style = Typography.displayLarge,
                            fontSize = 30.sp,
                        )
                        Image(
                            modifier = Modifier.size(200.dp),
                            painter =
                                painterResource(
                                    id =
                                        if (teamId == 1L) {
                                            R.drawable.img_winner_mal
                                        } else {
                                            R.drawable.img_winner_lang
                                        },
                                ),
                            contentDescription = "",
                        )
                    }
                    LongBlackButton(
                        onClick = { onDismiss() },
                        text = stringResource(id = R.string.game_win_confirm_message),
                        modifier = modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 15.dp),
                    )
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun RedGameWinDialogPreview() {
    MallangTheme {
        GameWinDialog(
            teamId = 1L,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BlueGameWinDialogPreview() {
    MallangTheme {
        GameWinDialog(
            teamId = 2L,
        )
    }
}
