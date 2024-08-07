package com.chill.mallang.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.dp
import com.chill.mallang.ui.theme.Red01
import com.chill.mallang.ui.theme.SkyBlue
import com.chill.mallang.ui.theme.Typography

// 팀 퍼센트 바
@Composable
fun PercentageBar(
    modifier: Modifier = Modifier,
    leftPercentage: Int,
    rightPercentage: Int,
    leftLabel: String,
    rightLabel: String,
    leftColor: Color,
    rightColor: Color,
) {
    Column(
        modifier =
            modifier
                .padding(horizontal = 30.dp),
    ) {
        Canvas(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(15.dp),
        ) {
            val path =
                Path().apply {
                    addRoundRect(
                        // 모서리가 둥근 사각형을 path에 추가
                        RoundRect(
                            Rect(
                                offset = Offset(0f, 0f),
                                size = Size(size.width, 30f),
                            ),
                            CornerRadius(15.dp.toPx(), 15.dp.toPx()),
                        ),
                    )
                }

            clipPath(path) {
                val barWidth = size.width
                val dividerRatio = leftPercentage.toFloat() / (leftPercentage + rightPercentage)
                val leftWidth = barWidth * dividerRatio

                drawRect(
                    color = Red01,
                    topLeft = Offset(0f, 0f),
                    size = Size(leftWidth, 30f),
                )

                drawRect(
                    color = SkyBlue,
                    topLeft = Offset(leftWidth, 0f),
                    size = Size(barWidth - leftWidth, 30f),
                )
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "$leftLabel $leftPercentage%",
                style = Typography.displaySmall,
                color = leftColor,
            )
            Text(
                text = "$rightLabel $rightPercentage%",
                style = Typography.displaySmall,
                color = rightColor,
            )
        }
    }
}
