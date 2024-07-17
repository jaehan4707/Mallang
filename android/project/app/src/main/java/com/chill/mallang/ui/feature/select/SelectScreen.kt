package com.chill.mallang.ui.feature.select

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chill.mallang.R
import com.chill.mallang.ui.feature.nickname.TextWithIcon
import com.chill.mallang.ui.theme.BackGround
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Red01
import com.chill.mallang.ui.theme.SkyBlue
import com.chill.mallang.ui.theme.Typography

@Composable
fun SelectScreen() {
    Surface(
        color = BackGround
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.15f))
            Image(
                painter = painterResource(id = R.drawable.ic_title_small),
                contentDescription = null,
                modifier = Modifier.fillMaxHeight(0.17f)
            )
            Spacer(modifier = Modifier.weight(0.1f))
            TextWithIcon(text = "당신의 진영을 선택해 주세요", icon = R.drawable.ic_team)
            Box(modifier = Modifier.height(30.dp))
            PercentageBar(
                leftPercentage = 45,
                rightPercentage = 55,
                leftLabel = "말",
                rightLabel = "랑",
                leftColor = Red01,
                rightColor = SkyBlue
            )
            Box(modifier = Modifier.height(35.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                TeamButton(onClick = { }, text = "말", color = Red01)
                Box(modifier = Modifier.width(25.dp))
                TeamButton(onClick = { }, text = "랑", color = SkyBlue)
            }
            Spacer(modifier = Modifier.weight(0.6f))
        }
    }
}

// 팀 버튼
@Composable
fun TeamButton(onClick: () -> Unit, text: String, color: Color) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = White,
            containerColor = color
        ),
        shape = RoundedCornerShape(13.dp),
        modifier = Modifier
            .width(100.dp)
            .height(48.dp)
    ) {
        Text(
            text,
            style = Typography.displayLarge
        )
    }
}

// 팀 퍼센트 바
@Composable
fun PercentageBar(
    leftPercentage: Int,
    rightPercentage: Int,
    leftLabel: String,
    rightLabel: String,
    leftColor: Color,
    rightColor: Color
) {


    Column(
        modifier = Modifier
            .padding(horizontal = 30.dp)
    ) {
        Canvas(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
        ) {
            val barWidth = size.width
            val barHeight = size.height

            val leftWidth = barWidth * (leftPercentage / 100f)
            val rightWidth = barWidth * (rightPercentage / 100f)

            val leftPath = Path().apply {
                moveTo(0f, 0f)
                lineTo(leftWidth - 15.dp.toPx(), 0f)
                arcTo(
                    rect = Rect(
                        leftWidth - 30.dp.toPx(),
                        0f,
                        leftWidth,
                        barHeight
                    ),
                    startAngleDegrees = -90f,
                    sweepAngleDegrees = 180f,
                    forceMoveTo = false
                )
                lineTo(0f, barHeight)
                close()
            }

            val rightPath = Path().apply {
                moveTo(leftWidth, 0f)
                lineTo(leftWidth + rightWidth - 15.dp.toPx(), 0f)
                arcTo(
                    rect = Rect(
                        leftWidth + rightWidth - 30.dp.toPx(),
                        0f,
                        leftWidth + rightWidth,
                        barHeight
                    ),
                    startAngleDegrees = 270f,
                    sweepAngleDegrees = 180f,
                    forceMoveTo = false
                )
                lineTo(leftWidth, barHeight)
                close()
            }

            drawPath(
                path = leftPath,
                color = leftColor,
                style = Fill
            )

            drawPath(
                path = rightPath,
                color = rightColor,
                style = Fill
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$leftLabel $leftPercentage%",
                style = Typography.displaySmall,
                color = leftColor
            )
            Text(
                text = "$rightLabel $rightPercentage%",
                style = Typography.displaySmall,
                color = rightColor
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SelectScreenPreview() {
    MallangTheme {
        SelectScreen()
    }
}