package com.chill.ui.viewmodel

import com.chill.data.HomeTestData
import com.chill.data.HomeTestData.percentage
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.local.DataStoreRepository
import com.chill.mallang.data.repository.remote.UserRepository
import com.chill.mallang.ui.component.experiencebar.ExperienceState
import com.chill.mallang.ui.feature.home.HomeUiState
import com.chill.mallang.ui.feature.home.HomeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

internal class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private val userRepository: UserRepository = mockk()
    private val dataStoreRepository: DataStoreRepository = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `유저_정보를_받아오면_UiState를_Success로_업데이트합니다`() = runTest {
        coEvery { userRepository.getUserInfo() } returns flowOf(ApiResponse.Success(HomeTestData.user))
        viewModel = HomeViewModel(userRepository, dataStoreRepository)
        viewModel.getUserInfo()
        assertEquals(
            HomeUiState.LoadUserInfo(
                nickName = HomeTestData.user.nickName,
                factionId = HomeTestData.user.factionId,
                experienceState = ExperienceState.Static(
                    value = percentage,
                    level = HomeTestData.user.level,
                ),
            ), viewModel.uiState.value
        )
    }
}