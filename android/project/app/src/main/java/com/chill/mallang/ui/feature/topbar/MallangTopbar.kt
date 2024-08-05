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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.chill.mallang.R

@Composable
fun MallangTopbar(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val viewModel: TopbarViewModel = hiltViewModel()
    val title by viewModel.titleContent

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        BackButtion(
            onBack = { viewModel.onBack(navController) },
        )
        title()
        HomeButton(
            onHome = { viewModel.onHome(navController) },
        )
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
