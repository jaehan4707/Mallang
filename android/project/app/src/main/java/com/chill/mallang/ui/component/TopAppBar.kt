package com.chill.mallang.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chill.mallang.R
import com.chill.mallang.ui.theme.Typography

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    label: String,
    popUpBackStack: () -> Unit,
    navigateToHome: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        IconButton(
            onClick = { popUpBackStack() },
            modifier = Modifier.size(55.dp, 55.dp),
        ) {
            Icon(
                modifier = Modifier.size(45.dp, 45.dp),
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }
        Text(text = label, style = Typography.titleMedium)
        IconButton(
            onClick = { navigateToHome() },
            modifier = Modifier.size(55.dp, 55.dp),
        ) {
            Icon(
                modifier = Modifier.size(45.dp, 45.dp),
                painter = painterResource(id = R.drawable.ic_home),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }
    }
}