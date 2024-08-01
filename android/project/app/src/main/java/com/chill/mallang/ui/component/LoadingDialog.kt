package com.chill.mallang.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.chill.mallang.R
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoadingDialog(modifier: Modifier = Modifier) {
    val lottieComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading_white))
    Dialog(onDismissRequest = {}) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LottieAnimation(
                    composition = lottieComposition,
                    restartOnPlay = true,
                    iterations = LottieConstants.IterateForever,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxHeight(0.8f),
                )
                Text(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .basicMarquee(
                                iterations = Int.MAX_VALUE,
                                delayMillis = 0,
                                velocity = 100.dp,
                            ),
                    text =
                        (stringResource(id = R.string.loading_dialog_message) + "      ").repeat(
                            3,
                        ),
                    style = Typography.titleLarge,
                    color = Color.White,
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoadingDialogPreview() {
    MallangTheme {
        LoadingDialog()
    }
}
