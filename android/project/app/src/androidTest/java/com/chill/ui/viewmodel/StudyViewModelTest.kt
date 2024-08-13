package com.chill.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.chill.data.StudyTestData.studyQuiz
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.local.DataStoreRepository
import com.chill.mallang.data.repository.remote.StudyRepository
import com.chill.mallang.ui.feature.study.StudyState
import com.chill.mallang.ui.feature.study.StudyViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

internal class StudyViewModelTest {

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
    fun `학습_퀴즈_데이터를_불러오는데_성공하면_UiState를_Success로_업데이트합니다`() = runTest {
        val userId = dataStoreRepository.getUserId() ?: 0L
        coEvery { savedStateHandle.get<Long>("studyId") } returns -1L
        val studyId = savedStateHandle.get<Long>("studyId") ?: -1L
        coEvery { studyRepository.getStudyQuiz(userId) } returns flowOf(
            ApiResponse.Success(studyQuiz)
        )
        viewModel = StudyViewModel(savedStateHandle, studyRepository, dataStoreRepository)
        assertEquals(
            StudyState.Success(
                studyId = studyId,
                quizTitle = studyQuiz.quizTitle,
                quizScript = studyQuiz.quizScript,
                wordList = studyQuiz.wordList,
                isResultScreen = false,
            ), viewModel.studyState.value
        )
    }
}