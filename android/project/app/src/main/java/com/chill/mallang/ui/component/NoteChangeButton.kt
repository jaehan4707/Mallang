package com.chill.mallang.ui.component

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chill.mallang.R
import com.chill.mallang.ui.theme.Gray6
import com.chill.mallang.ui.theme.Typography

@Composable
fun NoteChangeButton(
    context: Context,
    isWordScreen: Boolean,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier.padding(12.dp),
        colors =
            ButtonDefaults.buttonColors(
                contentColor = White,
                containerColor = Gray6,
            ),
        shape = RoundedCornerShape(10.dp),
        onClick = onClick,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_change),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(7.dp))
            Text(
                text =
                    if (isWordScreen) {
                        context.getString(R.string.change_to_incorrect_note)
                    } else {
                        context.getString(
                            R.string.change_to_word_note,
                        )
                    },
                style = Typography.displayMedium,
            )
        }
    }
}
