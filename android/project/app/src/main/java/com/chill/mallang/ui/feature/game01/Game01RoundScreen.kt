package com.chill.mallang.ui.feature.game01

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.chill.mallang.R
import com.chill.mallang.ui.theme.MallangTheme
import com.chill.mallang.ui.theme.Typography

@Composable
fun Game01RoundScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(painter = painterResource(id = R.mipmap.malang), contentDescription = "말랑")
            Text(text = "라운드 1", style = Typography.headlineLarge, fontSize = 40.sp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Game01RoundScreenPreview(){
    MallangTheme {
        Game01RoundScreen()
    }
}
