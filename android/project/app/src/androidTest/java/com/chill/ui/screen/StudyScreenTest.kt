package com.chill.ui.screen

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import com.chill.data.StudyTestData.studyQuiz
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.local.DataStoreRepository
import com.chill.mallang.data.repository.remote.StudyRepository
import com.chill.mallang.ui.feature.study.HandleStudyUi
import com.chill.mallang.ui.feature.study.StudyViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class StudyScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var viewModel: StudyViewModel
    private val studyRepository: StudyRepository = mockk()
    private val dataStoreRepository: DataStoreRepository = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        coEvery { dataStoreRepository.getUserId() } returns 1L
    }

    @Test
    fun `불러온_학습_퀴즈_데이터들은_화면에_표시된다`() = runTest {
        val userId = dataStoreRepository.getUserId() ?: 0L
        coEvery { savedStateHandle.get<Long>("studyId") } returns -1L
        coEvery { studyRepository.getStudyQuiz(userId) } returns flowOf(
            ApiResponse.Success(studyQuiz)
        )
        viewModel = StudyViewModel(savedStateHandle, studyRepository, dataStoreRepository)
        composeTestRule.setContent {
            HandleStudyUi(studyState = viewModel.studyState.value, studyViewModel = viewModel)
        }
        composeTestRule.onNodeWithTag("study_quiz_title")
            .assertTextEquals(studyQuiz.quizTitle)
        composeTestRule.onNodeWithTag("study_quiz_script")
            .assertTextEquals(studyQuiz.quizScript)
        composeTestRule.onNodeWithTag("study_quiz_list").onChildren()
            .assertCountEquals(studyQuiz.wordList.size)
    }

    @Test
    fun `보기를_클릭하면_체크가_표시된다`() = runTest {
        val userId = dataStoreRepository.getUserId() ?: 0L
        coEvery { savedStateHandle.get<Long>("studyId") } returns -1L
        coEvery { studyRepository.getStudyQuiz(userId) } returns flowOf(
            ApiResponse.Success(studyQuiz)
        )
        viewModel = StudyViewModel(savedStateHandle, studyRepository, dataStoreRepository)
        composeTestRule.setContent {
            HandleStudyUi(studyState = viewModel.studyState.value, studyViewModel = viewModel)
        }
        val clickIndex = 0
        composeTestRule.onNodeWithTag("study_quiz_$clickIndex").performClick()
        composeTestRule.onNodeWithContentDescription("study_check_$clickIndex").assertIsDisplayed()
    }
}