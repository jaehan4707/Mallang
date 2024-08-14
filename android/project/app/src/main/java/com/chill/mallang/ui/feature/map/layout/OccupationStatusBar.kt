package com.chill.mallang.ui.feature.map.layout

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Red01
import com.chill.mallang.ui.theme.SkyBlue
import com.chill.mallang.ui.theme.galmuri11Family

/**
 * 점령지 상태 바
 * @param leftCount : 왼쪽 점령지 수 ( 텍스트로 표시된다 )
 * @param rightCount : 오른쪽 점렴지 수 ( 텍스트로 표시된다 )
 * @param edgePadding : 양 끝단 padding. 양쪽 끝에 추가된다.
 */
@Composable
fun OccupationStatusBar(
    modifier: Modifier = Modifier,
    leftCount: Int,
    rightCount: Int,
    leftColor: Color = Red01,
    rightColor: Color = SkyBlue,
    height: Dp = 42.dp,
    cornerRadius: Dp = 8.dp,
    edgePadding: Dp = 8.dp,
    textStyle: TextStyle = TextStyle(
        fontFamily = galmuri11Family,
        fontSize = 24.sp,
        color = Color.White
    )
) {
    val textMeasurer = rememberTextMeasurer()
    val dividerRatio = if(leftCount+rightCount == 0) 0.5f else leftCount.toFloat() / (leftCount+rightCount)

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        val dividerX = size.width * dividerRatio

        // 왼쪽 박스
        clipRect(right = dividerX) {
            drawRoundRect(
                color = leftColor,
                size = size,
                cornerRadius = CornerRadius(cornerRadius.toPx())
            )
        }

        // 오른쪽 박스
        clipRect(left = dividerX) {
            drawRoundRect(
                color = rightColor,
                size = size,
                cornerRadius = CornerRadius(cornerRadius.toPx())
            )
        }

        //왼쪽 텍스트
        val leftText = "$leftCount"
        val leftTextSize = textMeasurer.measure(leftText, textStyle)
        drawText(
            textMeasurer = textMeasurer,
            text = leftText,
            style = textStyle,
            topLeft = Offset(
                edgePadding.toPx(),
                size.height / 2 - leftTextSize.size.height / 2
            )
        )

        //오른쪽 텍스트
        val rightText = "$rightCount"
        val rightTextSize = textMeasurer.measure(rightText, textStyle)
        drawText(
            textMeasurer = textMeasurer,
            text = rightText,
            style = textStyle,
            topLeft = Offset(
                size.width - rightTextSize.size.width - edgePadding.toPx(),
                size.height / 2 - rightTextSize.size.height / 2
            )
        )
    }
}

@Preview
@Composable
fun OccupationStatusBarPreview(){
    MallangTheme {
        OccupationStatusBar(leftCount = 0, rightCount = 0)
    }
}