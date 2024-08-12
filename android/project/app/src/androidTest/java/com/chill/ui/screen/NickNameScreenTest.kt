package com.chill.ui.screen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.chill.data.NickNameTestData.NO_DUPLICATED_NICKNAME
import com.chill.mallang.data.repository.remote.UserRepository
import com.chill.mallang.ui.feature.nickname.NicknameScreen
import com.chill.mallang.ui.feature.nickname.NicknameViewModel
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NicknameScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: NicknameViewModel
    private val userRepository: UserRepository = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = NicknameViewModel(userRepository)
    }

    @Test
    fun `전체_지우기_버튼을_누르면_닉네임은_지워져야한다`() {
        composeTestRule.setContent {
            NicknameScreen(viewModel = viewModel)
        }
        viewModel.nicknameState.updateNickname(NO_DUPLICATED_NICKNAME)
        composeTestRule.onNodeWithTag("clear_button").performClick()
        assertEquals(
            viewModel.nicknameState.nickname,
            "",
        )
    }
}