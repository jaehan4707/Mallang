package com.chill.ui.viewmodel

import com.chill.data.WordNoteTestData
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.local.DataStoreRepository
import com.chill.mallang.data.repository.remote.StudyRepository
import com.chill.mallang.ui.feature.word.WordNoteState
import com.chill.mallang.ui.feature.word.WordNoteViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

internal class WordNoteViewModelTest {
    private lateinit var viewModel: WordNoteViewModel
    private val studyRepository: StudyRepository = mockk()
    private val dataStoreRepository: DataStoreRepository = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        coEvery { dataStoreRepository.getUserId() } returns 1L
    }

    @Test
    fun `단어장_불러오기를_성공할경우_UiState를_Success로_업데이트_한다`() = runTest {
        val userId = dataStoreRepository.getUserId() ?: 0L
        coEvery { studyRepository.getWordList(userId) } returns flowOf(
            ApiResponse.Success(
                WordNoteTestData.wordList
            )
        )
        viewModel = WordNoteViewModel(studyRepository, dataStoreRepository)
        assertEquals(
            WordNoteState.Success(WordNoteTestData.wordList),
            viewModel.wordNoteState.value
        )
    }

    @Test
    fun `단어장_불러오기를_실패할경우_UiState를_Error로_업데이트_한다`() = runTest {
        val userId = dataStoreRepository.getUserId() ?: 0L
        coEvery { studyRepository.getWordList(userId) } returns flowOf(
            ApiResponse.Error(errorMessage = WordNoteTestData.loadErrorMessage)
        )
        viewModel = WordNoteViewModel(studyRepository, dataStoreRepository)
        assertEquals(
            WordNoteState.Error(WordNoteTestData.loadErrorMessage),
            viewModel.wordNoteState.value
        )
    }
}