package com.chill.mallang.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.chill.mallang.ui.feature.home.HomeScreen
import com.chill.mallang.ui.feature.login.LoginScreen
import com.chill.mallang.ui.feature.nickname.NicknameScreen
import com.chill.mallang.ui.feature.select.SelectScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MallangNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavHost(
        navController = navController,
        startDestination = DestinationLogin.route
    ) {

        composable(
            route = DestinationLogin.route
        ) {
            LoginScreen(onLoginSuccess = { loginUiState ->
                navController.navigate(DestinationNickName.createRoute(loginUiState))
            })
        }
        composable(
            route = DestinationNickName.routeWithArgs,
            arguments = DestinationNickName.arguments,
        ) { navBackStackEntry ->
            val userName = navBackStackEntry.arguments?.getString("userName") ?: ""
            val userEmail = navBackStackEntry.arguments?.getString("userEmail") ?: ""
            val userProfileImageUrl =
                URLEncoder.encode(
                    navBackStackEntry.arguments?.getString("userProfileImageUrl") ?: "",
                    StandardCharsets.UTF_8.toString()
                )
            NicknameScreen(modifier = modifier, onClick = { nickName ->
                navController.navigate(
                    DestinationSelect.createRoute(
                        userName = userName,
                        userEmail = userEmail,
                        userProfileImageUrl = userProfileImageUrl,
                        userNickName = nickName
                    )
                )
            })
        }

        composable(
            route = DestinationSelect.routeWithArgs,
            arguments = DestinationSelect.arguments,
        ) { navBackStackEntry ->
            val userName = navBackStackEntry.arguments?.getString("userName")
            val userEmail = navBackStackEntry.arguments?.getString("userEmail")
            val userProfileImageUrl = navBackStackEntry.arguments?.getString("userProfileImageUrl")
            val userNickName = navBackStackEntry.arguments?.getString("userNickName")
            SelectScreen(navigateToMain = { navController.navigate(DestinationMain.route) },
                signUp = {

                })
        }

        composable(
            route = DestinationMain.route,
        ) {
            HomeScreen(modifier = modifier)
        }
    }
}
