package com.chill.mallang.ui.feature.map.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chill.mallang.R
import com.chill.mallang.ui.theme.Gray3
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun LocateNearbyButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
){
    Button(
        modifier = modifier,
        onClick = onClick,
        contentPadding = PaddingValues(start = 8.dp, end = 16.dp),
        colors = ButtonColors(
            Color.Black,
            Color.Black,
            Gray3,
            Gray3
        )
    ) {
        Icon(painterResource(id = R.drawable.ic_locate), contentDescription = null, tint = Color.White)
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        Text(stringResource(id = R.string.button_search_nearby), color = Color.White)
    }
}

@Preview
@Composable
private fun SearchNearbyButtonPreview(){
    MallangTheme {
        LocateNearbyButton()
    }
}