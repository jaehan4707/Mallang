package com.chill.mallang.ui.feature.map.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.chill.mallang.R
import com.chill.mallang.ui.theme.MallangTheme

@Composable
fun CharacterMessageBox(
    modifier: Modifier = Modifier,
){
    Box(
        modifier = modifier.fillMaxSize()
    ){
        Image(
            modifier = Modifier.align(Alignment.Center),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = ""
        )
        Text(
            modifier = Modifier.align(Alignment.TopEnd),
            text = "목적지에 도착했어요!"
        )
    }
}

@Preview
@Composable
fun CharacterMessageBoxPreview(){
    MallangTheme {
        CharacterMessageBox()
    }
}