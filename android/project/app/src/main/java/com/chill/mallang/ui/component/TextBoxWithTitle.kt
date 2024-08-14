package com.chill.mallang.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.ui.theme.Gray3
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography

@Composable
fun TextBoxWithTitle(
    title: String,
    content: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(12.dp)
                .border(width = 1.5.dp, color = Gray6, shape = RoundedCornerShape(10.dp)),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp),
        ) {
            Text(
                text = title,
                style = Typography.titleSmall,
                textAlign = TextAlign.Center,
                fontSize = 19.sp,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
            )
            Box(modifier = Modifier.height(15.dp))
            Spacer(
                modifier =
                    Modifier
                        .height(2.dp)
                        .fillMaxWidth()
                        .background(Gray3),
            )
            Box(modifier = Modifier.height(15.dp))
            Text(
                text = content,
                style = Typography.titleSmall,
                fontSize = 15.sp,
                lineHeight = 25.sp,
                modifier = Modifier.padding(horizontal = 10.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextBoxWithTitlePreview() {
    MallangTheme {
        TextBoxWithTitle(
            title = "제목",
            content = "내용",
        )
    }
}
