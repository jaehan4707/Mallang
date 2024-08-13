package com.chill.ui.screen

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.chill.data.HomeTestData
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.local.DataStoreRepository
import com.chill.mallang.data.repository.remote.UserRepository
import com.chill.mallang.ui.feature.home.HomeScreen
import com.chill.mallang.ui.feature.home.HomeViewModel
import com.chill.mallang.ui.sound.SoundManager
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var viewModel: HomeViewModel
    private val userRepository: UserRepository = mockk()
    private val dataStoreRepository: DataStoreRepository = mockk()
    private val soundManager: SoundManager = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = HomeViewModel(userRepository, dataStoreRepository, soundManager)
        coEvery { soundManager.playBackgroundMusic(any()) } just Runs
        coEvery { soundManager.stopBackgroundMusic(any()) } just Runs
    }

    @Test
    fun `유저_정보_조회_성공시_유저의_닉네임이_표시되어야한다`() =
        runTest {
            coEvery { userRepository.getUserInfo() } returns flowOf(ApiResponse.Success(HomeTestData.user))
            composeTestRule.setContent {
                HomeScreen(viewModel = viewModel)
            }
            viewModel.getUserInfo()
            composeTestRule
                .onNodeWithTag("home_nickname")
                .assertTextEquals(HomeTestData.user.nickName)
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset after tests
    }
}
