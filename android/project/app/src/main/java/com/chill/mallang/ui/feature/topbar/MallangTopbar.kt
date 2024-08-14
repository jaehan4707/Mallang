package com.chill.mallang.ui.feature.topbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.chill.mallang.R
import com.chill.mallang.ui.theme.MallangTheme

/**
 * ### 상단바
 *
 * 뒤로가기 버튼, 제목, 홈버튼으로 이루어져 있다.
 *
 * `TopbarHandler` 를 통해 해당 상단바의 요소를 변경할 수 있다.
 * @see TopbarHandler
 */
@Composable
fun MallangTopbar(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val viewModel: TopbarViewModel = hiltViewModel()
    val isVisible by viewModel.isVisible
    val title by viewModel.titleContent
    val onBack by viewModel.onBack
    val onHome by viewModel.onHome

    if (isVisible) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            BackButtion(
                onBack = { onBack(navController) },
            )
            title()
            HomeButton(
                onHome = { onHome(navController) },
            )
        }
    }
}

@Composable
fun BackButtion(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
) {
    IconButton(
        modifier = modifier.size(55.dp, 55.dp),
        onClick = onBack,
    ) {
        Icon(
            modifier = Modifier.size(45.dp, 45.dp),
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            tint = Color.Unspecified,
        )
    }
}

@Composable
fun HomeButton(
    modifier: Modifier = Modifier,
    onHome: () -> Unit,
) {
    IconButton(
        modifier = modifier.size(55.dp, 55.dp),
        onClick = onHome,
    ) {
        Icon(
            modifier = Modifier.size(45.dp, 45.dp),
            painter = painterResource(id = R.drawable.ic_home),
            contentDescription = null,
            tint = Color.Unspecified,
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MallangTopbarPreview() {
    MallangTheme {
        MallangTopbar(navController = rememberNavController())
    }
}