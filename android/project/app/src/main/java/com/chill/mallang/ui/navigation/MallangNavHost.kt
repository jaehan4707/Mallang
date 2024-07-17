package com.chill.mallang.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MallangNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavHost(
        navController = navController,
        startDestination = DestinationMain.route
    ) {
        composable(
            route = DestinationMain.route
        ){

        }
        composable(
            route = DestinationUserDetail.routeWithArgs,
            arguments = DestinationUserDetail.arguments
        ){

        }
    }
}
