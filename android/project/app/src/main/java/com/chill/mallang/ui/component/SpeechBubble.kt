package com.chill.mallang.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun SpeechBubble(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp,
    tailHeight: Dp = 16.dp,
    tailWidth: Dp = 8.dp,
    tailPosition: Float = 0.5f,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier,
        shape =
            SpeechBubbleShape(
                cornerRadius = cornerRadius,
                tailHeight = tailHeight,
                tailWidth = tailWidth,
                tailPosition,
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Box(
            modifier = Modifier.padding(bottom = 16.dp),
        ) {
            content()
        }
    }
}

class SpeechBubbleShape(
    private val cornerRadius: Dp,
    private val tailHeight: Dp,
    private val tailWidth: Dp,
    private val tailPosition: Float,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        with(density) {
            val path =
                Path().apply {
                    // Draw rounded rectangle
                    addRoundRect(
                        RoundRect(
                            rect =
                                Rect(
                                    offset = Offset.Zero,
                                    size = Size(size.width, size.height - tailHeight.toPx()),
                                ),
                            cornerRadius = CornerRadius(cornerRadius.toPx()),
                        ),
                    )
                    // Draw tail
                    moveTo(size.width / 2f - tailWidth.toPx(), size.height - tailHeight.toPx())
                    lineTo(size.width * tailPosition, size.height)
                    lineTo(size.width / 2f + tailWidth.toPx(), size.height - tailHeight.toPx())
                    close()
                }
            return Outline.Generic(path)
        }
    }
}

@Preview
@Composable
fun SpeechBubblePreview() {
    SpeechBubble(
        tailPosition = 0.2f,
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Hello World!",
        )
    }
}
