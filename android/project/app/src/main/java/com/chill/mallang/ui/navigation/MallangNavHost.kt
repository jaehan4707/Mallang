package com.chill.mallang.ui.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.chill.mallang.ui.feature.fort_detail.FortDetailScreen
import com.chill.mallang.ui.feature.game_lobby.GameLobbyScreen
import com.chill.mallang.ui.feature.home.HomeScreen
import com.chill.mallang.ui.feature.login.LoginScreen
import com.chill.mallang.ui.feature.map.MapScreen
import com.chill.mallang.ui.feature.nickname.NicknameScreen
import com.chill.mallang.ui.feature.select.SelectScreen
import com.chill.mallang.ui.feature.study.StudyScreen
import com.chill.mallang.ui.feature.study_result.QuizResultScreen
import com.chill.mallang.ui.feature.word.WordNoteScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MallangNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String,
    onShowErrorSnackBar: (String) -> Unit,
) {
    val context = LocalContext.current
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(
            route = DestinationLogin.route,
        ) {
            LoginScreen(onLoginSuccess = { userEmail, userProfileImageUrl ->
                navController.navigate(
                    DestinationNickName.createRoute(userEmail, userProfileImageUrl),
                ) {
                    popUpTo(DestinationLogin.route) {
                        inclusive = false
                    }
                }
            }, onAuthLoginSuccess = {
                navController.navigate(DestinationMain.route) {
                    popUpTo(DestinationLogin.route) {
                        inclusive = false
                    }
                }
            })
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
            NicknameScreen(
                modifier = modifier,
                onSuccess = { nickName ->
                    navController.navigate(
                        DestinationSelect.createRoute(
                            userEmail = userEmail,
                            userProfileImageUrl = userProfileImageUrl,
                            userNickName = nickName,
                        ),
                    ) {
                        popUpTo(DestinationLogin.route) {
                            inclusive = false
                        }
                    }
                },
                popUpBackStack = navController::popBackStack,
            )
        }

        composable(
            route = DestinationSelect.routeWithArgs,
            arguments = DestinationSelect.arguments,
        ) {
            SelectScreen(navigateToMain = {
                navController.navigate(DestinationMain.route) {
                    popUpTo(DestinationLogin.route) {
                        inclusive = false
                    }
                }
            })
        }

        composable(
            route = DestinationMain.route,
        ) {
            HomeScreen(
                modifier = Modifier,
                navigateToGame = { navController.navigate(DestinationMap.route) },
                navigateToWordNote = { navController.navigate(DestinationWordNote.route) },
                popUpBackStack = navController::popBackStack,
                onShowErrorSnackBar = onShowErrorSnackBar,
                exitApplication = { (context as Activity).finish() },
            )
        }

        composable(
            route = DestinationWordNote.route,
        ) {
            WordNoteScreen(
                modifier = modifier,
                navigateToQuiz = {
                    navController.navigate(
                        DestinationQuiz.createRoute(studyId = it),
                    ) {
                        popUpTo(DestinationMain.route) {
                            inclusive = false
                        }
                    }
                },
            )
        }

        composable(
            route = DestinationMap.route,
        ) {
            MapScreen(
                onShowAreaDetail = { area ->
                    navController.navigate(DestinationAreaDetail.createRoute(area.areaId))
                },
            )
        }

        composable(
            route = DestinationAreaDetail.routeWithArgs,
            arguments = DestinationAreaDetail.arguments,
        ) { navBackStackEntry ->
            val areaId = navBackStackEntry.arguments?.getLong(DestinationAreaDetail.arg)
            FortDetailScreen(areaId = 1, userId = 1, teamId = 1)
        }

        composable(
            route = DestinationQuiz.routeWithArgs,
            arguments =
                listOf(
                    navArgument("studyId") { type = NavType.IntType },
                ),
        ) { backStackEntry ->
            StudyScreen(
                modifier = modifier,
                navigateToQuizResult = {
                    navController.navigate(
                        DestinationQuizResult.createRoute(
                            userAnswer = it,
                        ),
                    ) {
                        popUpTo(DestinationMain.route) {
                            inclusive = false
                        }
                    }
                },
                studyId = backStackEntry.arguments?.getInt("studyId") ?: -1,
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
            val userAnswer = navBackStackEntry.arguments?.getInt("userAnswer")

            QuizResultScreen(
                modifier = modifier,
                userAnswer = userAnswer ?: -1,
            )
        }
    }
}
