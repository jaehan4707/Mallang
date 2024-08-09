package com.chill.mallang.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.chill.mallang.ui.theme.Sub1
import com.chill.mallang.ui.theme.Typography

@Composable
fun BoldColoredText(
    modifier: Modifier = Modifier,
    text: String = "",
    textColor: Color = Sub1,
) {
    Text(
        text = text,
        color = textColor,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style =
            Typography.headlineSmall.merge(
                TextStyle(
                    color = Color.White,
                    drawStyle = Stroke(width = 5f, join = StrokeJoin.Round),
                ),
            ),
    )
}
