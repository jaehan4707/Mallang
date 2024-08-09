package com.chill.mallang.ui.feature.home.layout

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chill.mallang.R
import com.chill.mallang.ui.theme.Gray4
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun SettingsButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    IconButton(
        modifier =
            modifier
                .border(width = 4.dp, color = Gray4, shape = CircleShape),
        colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White),
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_setting),
            contentDescription = stringResource(id = R.string.side_button_setting),
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsButtonPreview() {
    MallangTheme {
        SettingsButton(modifier = Modifier)
    }
}
