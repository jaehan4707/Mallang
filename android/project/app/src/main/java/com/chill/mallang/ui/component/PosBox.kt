package com.chill.mallang.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.chill.mallang.ui.theme.Typography

@Composable
fun PosBox(
    modifier: Modifier = Modifier,
    pos: String,
) {
    Box(
        modifier =
        Modifier
            .background(
                color = PosColorProvider.getColorForPos(pos),
                shape = RoundedCornerShape(5.dp),
            ).padding(horizontal = 5.dp, vertical = 2.dp),
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = pos,
            style = Typography.displaySmall,
            color = Color.White,
        )
    }
}