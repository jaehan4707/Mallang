package com.chill.mallang.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.chill.mallang.ui.feature.topbar.MallangTopbar
import com.chill.mallang.ui.navigation.DestinationLogin
import com.chill.mallang.ui.navigation.MallangNavHost
import kotlinx.coroutines.launch

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()

    val snackBarHostState =
        remember {
            SnackbarHostState()
        }

    val onShowErrorSnackBar: (errorMessage: String) -> Unit = { errorMessage ->
        coroutineScope.launch {
            snackBarHostState.showSnackbar(errorMessage)
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            MallangTopbar(
                navController = navController,
            )
            MallangNavHost(
                navController = navController,
                startDestination = DestinationLogin.route,
                onShowErrorSnackBar = onShowErrorSnackBar,
            )
        }
    }
}
