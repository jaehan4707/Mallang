package com.chill.mallang.ui.feature.game.game01.SubView.PlayScreen.Layout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.R
import com.chill.mallang.ui.theme.Gray3
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography

@Composable
fun QuestionBody(
    systemMessage: String,
    quizScript: String,
) {
    Box(
        modifier =
            Modifier
                .padding(12.dp)
                .border(width = 1.5.dp, color = Gray6, shape = RoundedCornerShape(10.dp))
                .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 30.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(id = R.string.question_message_format, systemMessage),
                    style = Typography.titleSmall,
                    fontSize = 20.sp,
                )
            }
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
                text = quizScript,
                style = Typography.titleSmall,
                fontSize = 15.sp,
                lineHeight = 25.sp,
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun QuestionBodyPreview() {
    MallangTheme {
        QuestionBody(systemMessage = "", quizScript = "")
    }
}
