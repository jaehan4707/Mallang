package com.chill.mallang.ui.feature.setting

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.chill.mallang.R
import com.chill.mallang.ui.feature.home.ImageButton
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun SettingDialog(modifier: Modifier = Modifier, onClose: () -> Unit = {}) {
    var backGroundMusicChecked by remember {
        mutableStateOf(false)
    }
    var effectMusicChecked by remember {
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
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text(text = "설정")
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f), contentAlignment = Alignment.TopEnd
                    ) {
                        ImageButton(
                            icon = R.drawable.ic_close,
                            label = "",
                            onClick = onClose,
                        )
                    }
                }
                Row(
                    modifier = Modifier.wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "배경음")
                    Spacer(modifier = Modifier.weight(0.3f))
                    Switch(
                        checked = backGroundMusicChecked,
                        onCheckedChange = { backGroundMusicChecked = it },
                        thumbContent = {
                            if (backGroundMusicChecked) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_music_on),
                                    contentDescription = null,
                                    modifier = Modifier.size(SwitchDefaults.IconSize),
                                )
                            } else {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_music_off),
                                    contentDescription = null,
                                    modifier = Modifier.size(SwitchDefaults.IconSize),
                                )
                            }
                        }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "효과음")
                    Spacer(modifier = Modifier.weight(0.3f))
                    Switch(
                        checked = effectMusicChecked,
                        onCheckedChange = { effectMusicChecked = it },
                        thumbContent = {
                            if (effectMusicChecked) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_volume_on),
                                    contentDescription = null,
                                    modifier = Modifier.size(SwitchDefaults.IconSize),
                                )
                            } else {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_volume_off),
                                    contentDescription = null,
                                    modifier = Modifier.size(SwitchDefaults.IconSize),
                                )
                            }
                        })
                }


            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WordNotePreview() {
    MallangTheme {
        SettingDialog()
    }
}
