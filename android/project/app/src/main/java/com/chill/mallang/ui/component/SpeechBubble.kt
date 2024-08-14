package com.chill.mallang.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
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
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(width = 2.dp, color = Color.Black)
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
                    val width = size.width
                    val height= size.height
                    val cornerRadiusPx = cornerRadius.toPx()
                    val tailWidthPx = tailWidth.toPx()
                    val tailHeightPx = tailHeight.toPx()

                    // Top left corner
                    moveTo(cornerRadiusPx, 0f)

                    // Top line
                    lineTo(width - cornerRadiusPx, 0f)

                    // Top right corner
                    arcTo(
                        rect = Rect(
                            offset = Offset(width - 2 * cornerRadiusPx, 0f),
                            size = Size(2* cornerRadiusPx, 2 * cornerRadiusPx)
                        ),
                        startAngleDegrees = 270f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )

                    // Right line
                    lineTo(width, height - tailHeightPx - cornerRadiusPx)

                    // Bottom right corner
                    arcTo(
                        rect = Rect(
                            offset = Offset(width - 2 * cornerRadiusPx, height - tailHeightPx - 2 * cornerRadiusPx),
                            size = Size(2 * cornerRadiusPx, 2 * cornerRadiusPx)
                        ),
                        startAngleDegrees = 0f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )

                    // Tail
                    lineTo(width / 2 + tailWidthPx, height - tailHeightPx)
                    lineTo(width * tailPosition, height)
                    lineTo(width / 2 - tailWidthPx, height - tailHeightPx)

                    // Bottom left corner
                    arcTo(
                        rect = Rect(
                            offset = Offset(0f, height - tailHeightPx - 2 * cornerRadiusPx),
                            size = Size(2 * cornerRadiusPx, 2 * cornerRadiusPx)
                        ),
                        startAngleDegrees = 90f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )

                    // Left line
                    lineTo(0f, cornerRadiusPx)

                    // Top left corner (close path)
                    arcTo(
                        rect = Rect(
                            offset = Offset(0f, 0f),
                            size = Size(2 * cornerRadiusPx, 2 * cornerRadiusPx)
                        ),
                        startAngleDegrees = 180f,
                        sweepAngleDegrees = 90f,
                        forceMoveTo = false
                    )
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
