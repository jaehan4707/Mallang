package com.chill.mallang.ui.feature.home.layout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.Typography
import com.chill.mallang.ui.theme.Yellow01
import com.chill.mallang.ui.theme.Yellow02

@Composable
fun StudyButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Button(
        modifier =
            modifier.width(150.dp).shadow(elevation = 5.dp, shape = leftRoundedShape),
        onClick = onClick,
        shape = leftRoundedShape,
        // elevation = ButtonDefaults.buttonElevation(10.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = Yellow01,
                contentColor = Gray6,
            ),
        border = BorderStroke(8.dp, Yellow02),
    ) {
        Column(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.size(48.dp),
                text = "?",
                textAlign = TextAlign.Center,
                style = Typography.headlineLarge.copy(fontSize = 48.sp),
            )
            Text("학습 퀴즈", style = Typography.displayMedium.copy(fontSize = 32.sp))
        }
    }
}

val leftRoundedShape =
    RoundedCornerShape(topStart = 48.dp, bottomStart = 48.dp, topEnd = 8.dp, bottomEnd = 8.dp)

@Preview
@Composable
fun StudyButtonPreview() {
    StudyButton()
}
