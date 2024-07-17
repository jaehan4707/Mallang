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
    val route : String
}

/**
 * Argument를 받을 수 있는 라우팅 인터페이스
 */
interface ScreenDestinationsWithArgument : ScreenDestinations{
    /**
     * NavBackStackEntry.arguments.getLong([arg])
     */
    val arg: String
    val routeWithArgs: String
    val arguments: List<NamedNavArgument>
}

object DestinationMain : ScreenDestinations{
    override val route: String
        get() = ""
}
object DestinationMap : ScreenDestinations{
    override val route: String
        get() = "map"

}

object DestinationUserDetail : ScreenDestinationsWithArgument{
    override val route: String
        get() = "user_detail"

    override val arg = "user_id"
    override val routeWithArgs = "${this.route}/{$arg}"
    override val arguments = listOf(
        navArgument(name = arg) { type = NavType.LongType }
    )
}
