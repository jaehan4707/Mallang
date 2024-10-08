package com.chill.mallang.ui.feature.home.layout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.R
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.Green02
import com.chill.mallang.ui.theme.Green03
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography

@Composable
fun MapButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Button(
        modifier =
            modifier
                .width(150.dp)
                .shadow(elevation = 5.dp, shape = rightRoundedCornerShape),
        onClick = onClick,
        shape = rightRoundedCornerShape,
        // elevation = ButtonDefaults.buttonElevation(10.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = Green02,
                contentColor = Gray6,
            ),
        border = BorderStroke(8.dp, Green03),
    ) {
        Column(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, end = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = R.drawable.img_winner_lang),
                contentDescription = null,
            )
            Text("점령전", style = Typography.displayMedium.copy(fontSize = 32.sp))
        }
    }
}

val rightRoundedCornerShape =
    RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp, topEnd = 48.dp, bottomEnd = 48.dp)

@Preview
@Composable
fun MapButtonPreview() {
    MallangTheme {
        MapButton()
    }
}
