package com.chill.mallang.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.chill.mallang.R
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun AppCloseDialog() {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(usePlatformDefaultWidth = false),
        content = {
            Surface(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                        .padding(10.dp),
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(width = 2.dp, color = Gray6),
                color = Color.White,
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        modifier = Modifier.size(200.dp, 300.dp),
                        painter = painterResource(id = R.drawable.img_thank_you),
                        contentDescription = "",
                    )
                }
            }
        },
    )
}

@Preview
@Composable
fun AppCloseDialogPreview() {
    MallangTheme {
        AppCloseDialog()
    }
}
