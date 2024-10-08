package com.chill.mallang.ui.feature.topbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.chill.mallang.ui.navigation.DestinationMain

/**
 * ### 상단바 제어용 핸들러
 * `title` 또는 `content`를 사용해 상단바 제목을 커스텀할 수 있다.
 *
 * @param isVisible 상단바 노출 여부 ( true : 보임, false : 안 보임 )
 * @param title 상단바의 제목. `titleContent`가 NotNull이면 무시된다.
 * @param titleContent 상단바 커스텀 컴포넌트. 값이 존재한다면 `title`을 무시하고 표시된다.
 * @param onBack 뒤로가기.
 * @param onHome 홈으로.
 * ---
 * Example.
 * ```kotlin
 *     val (navController, setNavController) = remember { mutableStateOf<NavController?>(null) }
 *     val (isBackPressed, setBackPressed) = remember { mutableStateOf(false) }
 *
 *     BackConfirmHandler(
 *         isBackPressed = isBackPressed,
 *         onConfirm = {
 *             setBackPressed(false)
 *             navController?.popBackStack()
 *         },
 *         onDismiss = {
 *             setBackPressed(false)
 *         },
 *     )
 *     BackHandler(onBack = { setBackPressed(true) })
 *
 *     TopbarHandler(
 *         isVisible = true,
 *         title = "Some Title",
 *         onBack = { nav->
 *             setBackPressed(true)
 *             setNavController(nav)
 *         }
 *     )
 * ```
 *
 */
@Composable
fun TopbarHandler(
    key: Any? = null,
    isVisible: Boolean = false,
    title: String = "",
    titleContent: (@Composable () -> Unit)? = null,
    onBack: (NavController) -> Unit = { navController -> navController.popBackStack() },
    onHome: (NavController) -> Unit = { navController ->
        navController.popBackStack(
            DestinationMain.route,
            inclusive = false,
        )
    },
) {
    val viewModel: TopbarViewModel = hiltViewModel()

    LaunchedEffect(key, isVisible) {
        viewModel.updateVisibility(isVisible)
    }

    LaunchedEffect(key, title, titleContent) {
        if (titleContent == null) {
            viewModel.updateTitle(title)
        } else {
            viewModel.updateTitle(titleContent)
        }
    }

    LaunchedEffect(key, onBack) {
        viewModel.updateOnBack(onBack)
    }

    LaunchedEffect(key, onHome) {
        viewModel.updateOnHome(onHome)
    }
}
