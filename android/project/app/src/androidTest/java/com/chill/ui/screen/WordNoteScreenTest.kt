package com.chill.ui.screen

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.chill.data.WordNoteTestData.loadErrorMessage
import com.chill.data.WordNoteTestData.wordList
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.local.DataStoreRepository
import com.chill.mallang.data.repository.remote.StudyRepository
import com.chill.mallang.ui.feature.word.HandleWordNoteUi
import com.chill.mallang.ui.feature.word.WordNoteViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WordNoteScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: WordNoteViewModel
    private val studyRepository: StudyRepository = mockk()
    private val dataStoreRepository: DataStoreRepository = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        coEvery { dataStoreRepository.getUserId() } returns 1L
    }

    @Test
    fun `불러온_단어장들은_화면에_표시된다`() = runTest {
        val userId = dataStoreRepository.getUserId() ?: 1L
        coEvery { studyRepository.getWordList(userId) } returns flowOf(
            ApiResponse.Success(wordList)
        )
        viewModel = WordNoteViewModel(studyRepository, dataStoreRepository)
        composeTestRule.setContent {
            HandleWordNoteUi(uiState = viewModel.wordNoteState.value)
        }
        composeTestRule.onNodeWithTag("word_note_list").onChildren()
            .assertCountEquals(wordList.size)
    }

    @Test
    fun `단어장을_불러오는데_실패하면_에러메시지가_출력된다`() = runTest {
        val userId = dataStoreRepository.getUserId() ?: 1L
        coEvery { studyRepository.getWordList(userId) } returns flowOf(
            ApiResponse.Error(errorMessage = loadErrorMessage)
        )
        viewModel = WordNoteViewModel(studyRepository, dataStoreRepository)
        composeTestRule.setContent {
            HandleWordNoteUi(uiState = viewModel.wordNoteState.value)
        }
        composeTestRule.onNodeWithTag("word_note_error_message").assertIsDisplayed()
        composeTestRule.onNodeWithTag("word_note_error_message")
            .assertTextEquals(loadErrorMessage)
    }

    @Test
    fun `단어를_클릭하면_단어장_다이얼로그가_화면에_출력된다`() = runTest {
        val userId = dataStoreRepository.getUserId() ?: 1L
        coEvery { studyRepository.getWordList(userId) } returns flowOf(
            ApiResponse.Success(wordList)
        )
        viewModel = WordNoteViewModel(studyRepository, dataStoreRepository)
        composeTestRule.setContent {
            HandleWordNoteUi(uiState = viewModel.wordNoteState.value)
        }
        composeTestRule.onNodeWithTag("word_note_list")
            .onChildAt(0)
            .performClick()
        composeTestRule.onNodeWithTag("word_card_dialog").assertExists()
    }

    @Test
    fun `단어장_다이얼로그에는_클릭된_단어가_출력된다`() = runTest {
        val userId = dataStoreRepository.getUserId() ?: 1L
        coEvery { studyRepository.getWordList(userId) } returns flowOf(
            ApiResponse.Success(wordList)
        )
        viewModel = WordNoteViewModel(studyRepository, dataStoreRepository)
        composeTestRule.setContent {
            HandleWordNoteUi(uiState = viewModel.wordNoteState.value)
        }
        composeTestRule.onNodeWithTag("word_note_list")
            .onChildAt(0)
            .performClick()
        composeTestRule.onNodeWithTag("word_card_dialog_word")
            .assertTextEquals(wordList.first().word)
        composeTestRule.onNodeWithTag("word_card_dialog_example")
            .assertTextEquals(wordList.first().example)
        composeTestRule.onNodeWithTag("word_card_dialog_meaning")
            .assertTextEquals(wordList.first().meaning)
        composeTestRule.onNodeWithTag("word_card_dialog_pos").assertTextEquals(wordList.first().pos)
    }
}
