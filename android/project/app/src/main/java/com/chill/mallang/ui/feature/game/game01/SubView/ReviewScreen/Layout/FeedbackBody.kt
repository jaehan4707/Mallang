package com.chill.mallang.ui.feature.game.game01.SubView.ReviewScreen.Layout

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chill.mallang.R
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.QuokkaLightBrown
import com.chill.mallang.ui.theme.Typography
import kotlinx.coroutines.delay

@Composable
fun FeedbackBody(
    totalPoint: Float = 0F,
    modifier: Modifier = Modifier
) {
    var isTransparent by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (isTransparent) 1.0f else 0.0f,
        animationSpec = tween(durationMillis = 300),
    )

    val feedback_message =
        if(totalPoint >= 200){
            R.string.a_feedback_message
        } else if(totalPoint >= 150){
            R.string.b_feedback_message
        } else if(totalPoint >= 100){
            R.string.c_feedback_message
        } else if(totalPoint >= 50){
            R.string.d_feedback_message
        } else {
            R.string.f_feedback_message
        }

    LaunchedEffect(Unit) {
        delay(5000L)
        isTransparent = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier =
        modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .background(color = QuokkaLightBrown, shape = RoundedCornerShape(16.dp))
            .border(1.dp, Color.Black, shape = RoundedCornerShape(16.dp)),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(
                modifier = Modifier.height(30.dp),
            )
            Text(
                text = stringResource(id = feedback_message),
                style = Typography.displayLarge,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(horizontal = 50.dp)
                    .graphicsLayer(alpha = alpha),
            )
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 5.dp),
            ) {
                Image(
                    modifier =
                        modifier
                            .size(70.dp)
                            .align(Alignment.CenterEnd),
                    painter = painterResource(id = R.drawable.img_grader),
                    contentDescription = "",
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AFeedbackBodyPreview() {
    MallangTheme {
        FeedbackBody(
            totalPoint = 210F
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BFeedbackBodyPreview() {
    MallangTheme {
        FeedbackBody(
            totalPoint = 190F
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CFeedbackBodyPreview() {
    MallangTheme {
        FeedbackBody(
            totalPoint = 120F
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DFeedbackBodyPreview() {
    MallangTheme {
        FeedbackBody(
            totalPoint = 80F
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FFeedbackBodyPreview() {
    MallangTheme {
        FeedbackBody(
            totalPoint = 30F
        )
    }
}
