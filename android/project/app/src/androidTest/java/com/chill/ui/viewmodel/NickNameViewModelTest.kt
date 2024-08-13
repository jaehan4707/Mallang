package com.chill.ui.viewmodel

import com.chill.data.NickNameTestData.DUPLICATED_NICKNAME
import com.chill.data.NickNameTestData.INCLUDE_NUMBER_NICKNAME
import com.chill.data.NickNameTestData.INCLUDE_SPACE_NICKNAME
import com.chill.data.NickNameTestData.NO_DUPLICATED_NICKNAME
import com.chill.data.NickNameTestData.TOO_LONG_NICKNAME
import com.chill.data.NickNameTestData.TOO_SHORT_NICKNAME
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.remote.UserRepository
import com.chill.mallang.ui.feature.nickname.NickNameUiState
import com.chill.mallang.ui.feature.nickname.NicknameViewModel
import com.chill.mallang.ui.util.ErrorMessage
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

@OptIn(ExperimentalCoroutinesApi::class)
internal class NickNameViewModelTest {
    private lateinit var viewModel: NicknameViewModel
    private val userRepository: UserRepository = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = NicknameViewModel(userRepository)
    }

    @Test
    fun `닉네임이_중복되지않았다면_UiState를_Success로_업데이트_한다`() = runTest {
        val nickName = NO_DUPLICATED_NICKNAME
        coEvery { userRepository.checkNickName(nickName) } returns flowOf(ApiResponse.Success(Unit)) // 반환 flow는 ApiResponse.Success로 고정
        viewModel.nicknameState.updateNickname(nickName)
        viewModel.checkNickName()
        assertEquals(NickNameUiState.Success(nickName), viewModel.uiState.value)
    }

    @Test
    fun `닉네임이_중복되었다면_UiState를_Error로_업데이트_한다`() = runTest {
        val nickName = DUPLICATED_NICKNAME
        coEvery { userRepository.checkNickName(nickName) } returns flowOf(
            ApiResponse.Error(
                errorMessage = ErrorMessage.DUPLICATED_NICKNAME
            )
        )
        viewModel.nicknameState.updateNickname(nickName)
        viewModel.checkNickName()
        assertEquals(
            NickNameUiState.Error(errorMessage = ErrorMessage.DUPLICATED_NICKNAME),
            viewModel.uiState.value
        )
    }

    @Test
    fun `닉네임은_숫자를_포함하면_안된다`() = runTest {
        val nickName = INCLUDE_NUMBER_NICKNAME
        viewModel.nicknameState.updateNickname(nickName)
        assertEquals(
            viewModel.nicknameState.errorMessage,
            ErrorMessage.INVALID_CHAR,
        )
    }

    @Test
    fun `닉네임은_공백을_포함하면_안된다`() = runTest {
        val nickName = INCLUDE_SPACE_NICKNAME
        viewModel.nicknameState.updateNickname(nickName)
        assertEquals(
            viewModel.nicknameState.errorMessage,
            ErrorMessage.INVALID_CHAR,
        )
    }

    @Test
    fun `닉네임의_글자수는_2글자보다_작으면_안된다`() = runTest {
        val nickName = TOO_SHORT_NICKNAME
        viewModel.nicknameState.updateNickname(nickName)
        assertEquals(
            viewModel.nicknameState.errorMessage,
            ErrorMessage.TOO_SHORT,
        )
    }

    @Test
    fun `닉네임의_글자수는_10글자보다_많으면_안된다`() = runTest {
        val nickName = TOO_LONG_NICKNAME
        viewModel.nicknameState.updateNickname(nickName)
        assertEquals(
            viewModel.nicknameState.errorMessage,
            ErrorMessage.TOO_LONG,
        )
    }
}