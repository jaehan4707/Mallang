package com.chill.mallang.ui.feature.setting

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.chill.mallang.R
import com.chill.mallang.ui.component.MallangSwitch
import com.chill.mallang.ui.feature.home.ImageButton
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun SettingDialog(
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {},
) {
    val (backGroundMusicChecked, setBackGroundMusic) =
        remember {
            mutableStateOf(false)
        }
    val (effectMusicChecked, setEffectMusic) =
        remember {
            mutableStateOf(false)
        }
    val (notificationChecked, setNotification) =
        remember {
            mutableStateOf(false)
        }
    Dialog(onDismissRequest = { }) {
        Surface(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp),
            shape = RoundedCornerShape(15.dp),
            border = BorderStroke(width = 2.dp, color = Gray6),
            color = Color.White,
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text(text = stringResource(R.string.setting_title))
                    }
                    Box(
                        modifier =
                            Modifier
                                .weight(1f),
                        contentAlignment = Alignment.TopEnd,
                    ) {
                        ImageButton(
                            icon = R.drawable.ic_close,
                            label = "",
                            onClick = onClose,
                        )
                    }
                }
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.setting_background_music),
                        modifier = Modifier.fillMaxWidth(0.25f),
                    )
                    MallangSwitch(
                        checked = backGroundMusicChecked,
                        setChecked = setBackGroundMusic,
                        onIconImage = R.drawable.ic_volume_on,
                        offIconImage = R.drawable.ic_volume_off,
                    )
                    Spacer(modifier = Modifier.weight(0.1f))
                    Text(
                        text = stringResource(R.string.setting_effect_music),
                        modifier = Modifier.fillMaxWidth(0.4f),
                    )
                    MallangSwitch(
                        checked = effectMusicChecked,
                        setChecked = setEffectMusic,
                        onIconImage = R.drawable.ic_volume_on,
                        offIconImage = R.drawable.ic_volume_off,
                    )
                }
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Spacer(modifier = Modifier.weight(0.45f))
                    Text(text = stringResource(R.string.setting_notification))
                    Spacer(modifier = Modifier.weight(0.1f))
                    MallangSwitch(
                        checked = notificationChecked,
                        setChecked = setNotification,
                        onIconImage = R.drawable.ic_notification_on,
                        offIconImage = R.drawable.ic_notification_off,
                    )
                    Spacer(modifier = Modifier.weight(0.45f))
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SettingDialogPreview() {
    MallangTheme {
        SettingDialog()
    }
}
