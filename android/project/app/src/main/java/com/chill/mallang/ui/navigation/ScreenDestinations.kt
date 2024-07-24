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

object DestinationMap : ScreenDestinations {
    override val route: String
        get() = "map"

}

object DestinationNickName : ScreenDestinationsWithArgument {
    override val arg: String
        get() = "user"

    override val routeWithArgs: String
        get() = "${this.route}/{userName}/{userEmail}/{userProfileImageUrl}"

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(name = "userName") { type = NavType.StringType },
            navArgument(name = "userEmail") { type = NavType.StringType },
            navArgument(name = "userProfileImageUrl") { type = NavType.StringType }
        )

    override val route: String
        get() = "nickname"

    fun createRoute(
        userName: String,
        userEmail: String,
        userProfileImageUrl: String,
    ) =
        "${this.route}/${userName}/${userEmail}/${userProfileImageUrl}"

}

object DestinationSelect : ScreenDestinationsWithArgument {
    override val arg: String
        get() = "select"

    override val routeWithArgs: String
        get() = "${this.route}/{userName}/{userEmail}/{userProfileImageUrl}/{userNickName}"

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(name = "userName") { type = NavType.StringType },
            navArgument(name = "userEmail") { type = NavType.StringType },
            navArgument(name = "userProfileImageUrl") { type = NavType.StringType },
            navArgument(name = "userNickName") { type = NavType.StringType },
        )

    override val route: String
        get() = "select"

    fun createRoute(
        userName: String,
        userEmail: String,
        userProfileImageUrl: String,
        userNickName: String,
    ) = "${this.route}/${userName}/${userEmail}/${userProfileImageUrl}/${userNickName}"
}

object DestinationUserDetail : ScreenDestinationsWithArgument {
    override val route: String
        get() = "user_detail"

    override val arg = "user_id"
    override val routeWithArgs = "${this.route}/{$arg}"
    override val arguments = listOf(
        navArgument(name = arg) { type = NavType.LongType }
    )
}