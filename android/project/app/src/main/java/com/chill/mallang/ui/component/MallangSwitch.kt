package com.chill.mallang.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.chill.mallang.R
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun MallangSwitch(
    checked: Boolean = false,
    setChecked: (Boolean) -> Unit = {},
    onIconImage: Int = R.drawable.ic_volume_on,
    offIconImage: Int = R.drawable.ic_volume_off,
) {
    Switch(
        checked = checked,
        onCheckedChange = { setChecked(it) },
        thumbContent = {
            if (checked) {
                Icon(
                    painter = painterResource(id = onIconImage),
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                )
            } else {
                Icon(
                    painter = painterResource(id = offIconImage),
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                )
            }
        },
        colors =
            SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color.Black,
                uncheckedThumbColor = Color.Gray,
                uncheckedTrackColor = Color.White,
            ),
    )
}

@Preview(showBackground = true)
@Composable
fun MallangSwitchPreview() {
    MallangTheme {
        MallangSwitch()
    }
}
