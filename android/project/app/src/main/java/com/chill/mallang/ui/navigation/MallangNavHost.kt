package com.chill.mallang.ui.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chill.mallang.ui.feature.fort_detail.FortDetailScreen
import com.chill.mallang.ui.feature.game.game01.Game01Screen
import com.chill.mallang.ui.feature.game_lobby.GameLobbyScreen
import com.chill.mallang.ui.feature.home.HomeScreen
import com.chill.mallang.ui.feature.incorrect_word.IncorrectNoteScreen
import com.chill.mallang.ui.feature.login.LoginScreen
import com.chill.mallang.ui.feature.map.MapScreen
import com.chill.mallang.ui.feature.nickname.NicknameScreen
import com.chill.mallang.ui.feature.select.SelectScreen
import com.chill.mallang.ui.feature.study.StudyScreen
import com.chill.mallang.ui.feature.study_result.StudyResultScreen
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
                popUpBackStack = navController::popBackStack,
                navigateToStudy = {
                    navController.navigate(
                        DestinationStudy.createRoute(studyId = it),
                    ) {
                        popUpTo(DestinationWordNote.route) {
                            inclusive = false
                        }
                    }
                },
                navigateToIncorrectWord = {
                    navController.navigate(
                        DestinationIncorrectNote.route,
                    ) {
                        popUpTo(DestinationWordNote.route) {
                            inclusive = true
                        }
                    }
                },
            )
        }

        composable(
            route = DestinationIncorrectNote.route,
        ) {
            IncorrectNoteScreen(
                popUpBackStack = navController::popBackStack,
                navigateToWordNote = {
                    navController.navigate(DestinationWordNote.route) {
                        popUpTo(DestinationIncorrectNote.route) {
                            inclusive = true
                        }
                    }
                },
                navigateToStudy = {
                    navController.navigate(DestinationStudy.createRoute(studyId = it)) {
                        popUpTo(DestinationIncorrectNote.route) {
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
            FortDetailScreen(
                areaId = areaId,
                onStartGame = {
                    navController.navigate(
                        DestinationGame.createRoute(areaId = areaId ?: -1),
                    )
                },
            )
        }

        composable(
            route = DestinationStudy.routeWithArgs,
            arguments = DestinationStudy.arguments,
        ) {
            StudyScreen(
                modifier = modifier,
                navigateToStudyResult = { studyId, userAnswer ->
                    navController.navigate(
                        DestinationStudyResult.createRoute(
                            studyId = studyId,
                            userAnswer = userAnswer,
                        ),
                    ) {
                        popUpTo(DestinationWordNote.route) {
                            inclusive = false
                        }
                    }
                },
                popUpBackStack = navController::popBackStack,
            )
        }

        composable(
            route = DestinationStudyResult.routeWithArgs,
            arguments = DestinationStudyResult.arguments,
        ) { navBackStackEntry ->
            val userAnswer = navBackStackEntry.arguments?.getInt("userAnswer")

            StudyResultScreen(
                modifier = modifier,
                popUpBackStack = navController::popBackStack,
                userAnswer = userAnswer ?: -1,
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
            route = DestinationGame.routeWithArgs,
            arguments = DestinationGame.arguments,
        ) { navBackStackEntry ->
            val areaId = navBackStackEntry.arguments?.getLong(DestinationAreaDetail.arg)

            Game01Screen(
                modifier = modifier,
                areaId = areaId ?: -1,
                popUpBackStack = navController::popBackStack,
            )
        }
    }
}
