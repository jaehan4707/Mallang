package com.chill.mallang.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * 기본 라우팅 인터페이스
 * @param route
 */
interface ScreenDestinations {
    /**
     * NavGraph의 라우팅 경로
     */
    val route: String
}

/**
 * Argument를 받을 수 있는 라우팅 인터페이스
 */
interface ScreenDestinationsWithArgument : ScreenDestinations {
    /**
     * NavBackStackEntry.arguments.getLong([arg])
     */
    val arg: String
    val routeWithArgs: String
    val arguments: List<NamedNavArgument>
}

object DestinationLogin : ScreenDestinations {
    override val route: String
        get() = "login"
}

object DestinationMain : ScreenDestinations {
    override val route: String
        get() = "main"
}

/**
 * 지도화면
 */
object DestinationMap : ScreenDestinations {
    override val route: String
        get() = "map"
}

object DestinationAreaDetail : ScreenDestinationsWithArgument {
    override val arg: String
        get() = "areaId"
    override val routeWithArgs: String
        get() = "${this.route}/{areaId}"
    override val arguments: List<NamedNavArgument>
        get() =
            listOf(
                navArgument(name = arg) { type = NavType.LongType },
            )
    override val route: String
        get() = "area_detail"

    fun createRoute(areaId: Long) = "${this.route}/$areaId"
}

object DestinationNickName : ScreenDestinationsWithArgument {
    override val arg: String
        get() = "user"

    override val routeWithArgs: String
        get() = "${this.route}/{userEmail}/{userProfileImageUrl}"

    override val arguments: List<NamedNavArgument>
        get() =
            listOf(
                navArgument(name = "userEmail") { type = NavType.StringType },
                navArgument(name = "userProfileImageUrl") { type = NavType.StringType },
            )

    override val route: String
        get() = "nickname"

    fun createRoute(
        userEmail: String,
        userProfileImageUrl: String,
    ) = "${this.route}/$userEmail/$userProfileImageUrl"
}

object DestinationSelect : ScreenDestinationsWithArgument {
    override val arg: String
        get() = "select"

    override val routeWithArgs: String
        get() = "${this.route}/{userEmail}/{userProfileImageUrl}/{userNickName}"

    override val arguments: List<NamedNavArgument>
        get() =
            listOf(
                navArgument(name = "userEmail") { type = NavType.StringType },
                navArgument(name = "userProfileImageUrl") { type = NavType.StringType },
                navArgument(name = "userNickName") { type = NavType.StringType },
            )

    override val route: String
        get() = "select"

    fun createRoute(
        userEmail: String,
        userProfileImageUrl: String,
        userNickName: String,
    ) = "${this.route}/$userEmail/$userProfileImageUrl/$userNickName"
}

object DestinationUserDetail : ScreenDestinationsWithArgument {
    override val route: String
        get() = "user_detail"

    override val arg = "user_id"
    override val routeWithArgs = "${this.route}/{$arg}"
    override val arguments =
        listOf(
            navArgument(name = arg) { type = NavType.LongType },
        )
}

object DestinationWordNote : ScreenDestinations {
    override val route: String
        get() = "wordNote"
}

object DestinationIncorrectNote : ScreenDestinations {
    override val route: String
        get() = "incorrectNote"
}

object DestinationStudy : ScreenDestinationsWithArgument {
    override val arg: String
        get() = "study"
    override val routeWithArgs: String
        get() = "${this.route}/{studyId}"
    override val arguments: List<NamedNavArgument>
        get() =
            listOf(
                navArgument(name = "studyId") { type = NavType.LongType },
            )
    override val route: String
        get() = "study"

    fun createRoute(studyId: Int) = "${this.route}/$studyId"
}

object DestinationStudyResult : ScreenDestinationsWithArgument {
    override val arg: String
        get() = "study_result"

    override val routeWithArgs: String
        get() = "${this.route}/{studyId}/{userAnswer}"

    override val arguments: List<NamedNavArgument>
        get() =
            listOf(
                navArgument(name = "studyId") { type = NavType.LongType },
                navArgument(name = "userAnswer") { type = NavType.IntType },
            )

    override val route: String
        get() = "study_result"

    fun createRoute(
        studyId: Long,
        userAnswer: Int,
    ) = "${this.route}/$studyId/$userAnswer"
}

object DestinationGameLobby : ScreenDestinations {
    override val route: String
        get() = "GameLobby"
}

object DestinationGame : ScreenDestinationsWithArgument {
    override val arg: String
        get() = "areaId"
    override val routeWithArgs: String
        get() = "${this.route}/{areaId}"
    override val arguments: List<NamedNavArgument>
        get() =
            listOf(
                navArgument(name = arg) { type = NavType.LongType },
            )
    override val route: String
        get() = "game"

    fun createRoute(areaId: Long) = "${this.route}/$areaId"
}
