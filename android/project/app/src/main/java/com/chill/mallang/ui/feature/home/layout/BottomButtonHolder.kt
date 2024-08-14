package com.chill.mallang.ui.feature.home.layout

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chill.mallang.ui.theme.Gray2
import com.chill.mallang.ui.theme.MallangTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

@Composable
fun BottomButtonHolder(
    modifier: Modifier = Modifier,
    onClickStudy: () -> Unit = {},
    onClickMap: () -> Unit = {},
    onClickSetting: () -> Unit = {},
) {
    val eventFlow = remember { MutableStateFlow<ButtonEvent>(ButtonEvent.None) }
    val event by eventFlow.collectAsState()

    val height by animateIntAsState(
        targetValue = if (event is ButtonEvent.None) 210 else 0,
        animationSpec = tween(durationMillis = 300),
        finishedListener = { _ ->
            when (event) {
                ButtonEvent.Map -> onClickMap()
                ButtonEvent.Study -> onClickStudy()
                else -> {}
            }
        },
    )

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(height.dp)
                .clipToBounds()
                .background(color = Color.White)
                .border(width = 8.dp, color = Gray2)
                .border(
                    width = 8.dp,
                    color = Gray2,
                    shape = RoundedCornerShape(24.dp),
                ),
    ) {
        SettingsButton(
            modifier =
                Modifier
                    .padding(16.dp)
                    .size(40.dp)
                    .align(Alignment.TopEnd),
            onClick = onClickSetting,
        )
        Row(
            modifier =
                Modifier
                    .align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            StudyButton(onClick = {
                eventFlow.update { ButtonEvent.Study }
            })
            MapButton(onClick = {
                eventFlow.update { ButtonEvent.Map }
            })
        }
    }
}

sealed interface ButtonEvent {
    data object None : ButtonEvent

    data object Study : ButtonEvent

    data object Map : ButtonEvent
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BottomButtonHolderPreview() {
    MallangTheme {
        BottomButtonHolder()
    }
}
