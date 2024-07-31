package com.chill.mallang.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.chill.mallang.ui.component.BackConfirmHandler
import com.chill.mallang.ui.feature.quiz.TopAppBar
import com.chill.mallang.ui.navigation.DestinationLogin
import com.chill.mallang.ui.navigation.DestinationMain
import com.chill.mallang.ui.navigation.DestinationNickName
import com.chill.mallang.ui.navigation.DestinationSelect
import com.chill.mallang.ui.navigation.MallangNavHost

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val title = currentDestination?.route?.substringBefore("/") ?: ""
    val isShownShowAppBar =
        when (title) {
            DestinationLogin.route, DestinationSelect.route, DestinationNickName.route, DestinationMain.route, "" -> {
                false
            }

            else -> true
        }
    val isBackPressed = remember { mutableStateOf(false) }

    val onConfirm = {
        navController.popBackStack()
        isBackPressed.value = false
    }

    val onDismiss = {
        isBackPressed.value = false
    }

    BackConfirmHandler(
        onConfirm = onConfirm,
        onDismiss = { isBackPressed.value = it },
        isBackPressed = isBackPressed.value,
    )

    Scaffold(
        modifier = modifier,
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (isShownShowAppBar) {
                TopAppBar(
                    label = title,
                    popUpBackStack = { isBackPressed.value = true },
                    navigateToHome = {
                        navController.popBackStack(
                            DestinationMain.route,
                            inclusive = false,
                        )
                    },
                )
            }
            MallangNavHost(
                navController = navController,
                startDestination = DestinationLogin.route,
            )
        }
    }
}
