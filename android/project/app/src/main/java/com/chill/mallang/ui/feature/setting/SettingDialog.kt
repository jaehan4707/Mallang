package com.chill.mallang.ui.feature.setting

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chill.mallang.R
import com.chill.mallang.ui.component.LongBlackButton
import com.chill.mallang.ui.component.MallangSwitch
import com.chill.mallang.ui.feature.home.ImageButton
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun SettingDialog(
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {},
    onShowEditNickNameDialog: () -> Unit = {},
    onLogOut: () -> Unit = {},
    onSignOut: () -> Unit = {},
) {
    val viewModel: SettingViewModel = hiltViewModel()
    val backGroundMusicChecked by viewModel.backgroundVolume.collectAsStateWithLifecycle(false)
    val effectMusicChecked by viewModel.soundEffectsVolume.collectAsStateWithLifecycle(false)
    val notificationChecked by viewModel.notificationAlarm.collectAsStateWithLifecycle()
    Dialog(onDismissRequest = onClose) {
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
                        setChecked = viewModel::toggleBackgroundVolume,
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
                        setChecked = viewModel::toggleSoundEffectsVolume,
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
                        setChecked = viewModel::toggleNotificationAlarm,
                        onIconImage = R.drawable.ic_notification_on,
                        offIconImage = R.drawable.ic_notification_off,
                    )
                    Spacer(modifier = Modifier.weight(0.45f))
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    LongBlackButton(
                        modifier =
                            Modifier
                                .weight(1f)
                                .height(40.dp),
                        onClick = onShowEditNickNameDialog,
                        text = stringResource(R.string.setting_edit_nickname),
                    )
                    Spacer(modifier = Modifier.weight(0.5f))
                    LongBlackButton(
                        modifier =
                            Modifier
                                .weight(1f)
                                .height(40.dp),
                        onClick = onLogOut,
                        text = stringResource(R.string.setting_logout),
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                LongBlackButton(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                    onClick = onSignOut,
                    text = stringResource(R.string.setting_sign_out),
                )
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
