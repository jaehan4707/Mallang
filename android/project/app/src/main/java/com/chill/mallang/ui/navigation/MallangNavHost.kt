package com.chill.mallang.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.chill.mallang.ui.feature.game_lobby.GameLobbyScreen
import com.chill.mallang.ui.feature.home.HomeScreen
import com.chill.mallang.ui.feature.login.LoginScreen
import com.chill.mallang.ui.feature.nickname.NicknameScreen
import com.chill.mallang.ui.feature.quiz_result.QuizResultScreen
import com.chill.mallang.ui.feature.quiz.QuizScreen
import com.chill.mallang.ui.feature.quiz.WordNoteScreen
import com.chill.mallang.ui.feature.select.SelectScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MallangNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = DestinationLogin.route,
    ) {
        composable(
            route = DestinationLogin.route,
        ) {
            LoginScreen(onLoginSuccess = { userEmail, userProfileImageUrl ->
                navController.navigate(
                    DestinationNickName.createRoute(userEmail, userProfileImageUrl),
                )
            }, onAuthLoginSuccess = { navController.navigate(DestinationMain.route) })
        }
        composable(
            route = DestinationNickName.routeWithArgs,
            arguments = DestinationNickName.arguments,
        ) { navBackStackEntry ->
            val userEmail = navBackStackEntry.arguments?.getString("userEmail") ?: ""
            val userProfileImageUrl =
                URLEncoder.encode(
                    navBackStackEntry.arguments?.getString("userProfileImageUrl") ?: "",
                    StandardCharsets.UTF_8.toString(),
                )
            NicknameScreen(modifier = modifier, onSuccess = { nickName ->
                navController.navigate(
                    DestinationSelect.createRoute(
                        userEmail = userEmail,
                        userProfileImageUrl = userProfileImageUrl,
                        userNickName = nickName,
                    ),
                )
            })
        }

        composable(
            route = DestinationSelect.routeWithArgs,
            arguments = DestinationSelect.arguments,
        ) { navBackStackEntry ->
            val userEmail = navBackStackEntry.arguments?.getString("userEmail")
            val userProfileImageUrl = navBackStackEntry.arguments?.getString("userProfileImageUrl")
            val userNickName = navBackStackEntry.arguments?.getString("userNickName")
            SelectScreen(navigateToMain = { navController.navigate(DestinationMain.route) })
        }

        composable(
            route = DestinationMain.route,
        ) {
            HomeScreen(
                modifier = Modifier,
                navigateToGame = { navController.navigate(DestinationGameLobby.route) },
                navigateToWordNote = { navController.navigate(DestinationWordNote.route) },
            )
        }

        composable(
            route = DestinationWordNote.route,
        ) {
            WordNoteScreen(
                modifier = modifier,
                popUpBackStack = { navController.popBackStack() },
                navigateToQuiz = {
                    navController.navigate(DestinationQuiz.route) {
                        popUpTo(DestinationMain.route) {
                            inclusive = false
                        }
                    }
                },
            )
        }

        composable(
            route = DestinationQuiz.route,
        ) {
            QuizScreen(
                modifier = modifier,
                popUpBackStack = { navController.popBackStack() },
                navigateToQuizResult = {
                    navController.navigate(
                        DestinationQuizResult.createRoute(
                            studyId = it,
                        ),
                    ) {
                        popUpTo(DestinationMain.route) {
                            inclusive = false
                        }
                    }
                },
            )
        }

        composable(
            route = DestinationGameLobby.route,
        ) {
            GameLobbyScreen(
                modifier = modifier,
            )
        }

        composable(
            route = DestinationQuizResult.routeWithArgs,
            arguments = DestinationQuizResult.arguments,
        ) { navBackStackEntry ->
            val studyId = navBackStackEntry.arguments?.getInt("studyId")

            QuizResultScreen(
                modifier = modifier,
                popUpBackStack = {
                    navController.popBackStack(
                        DestinationMain.route,
                        inclusive = false,
                    )
                },
            )
        }
    }
}
