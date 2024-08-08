package com.chill.mallang.ui.feature.word

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.local.DataStoreRepository
import com.chill.mallang.data.repository.remote.StudyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordNoteViewModel
    @Inject
    constructor(
        private val studyRepository: StudyRepository,
        private val dataStoreRepository: DataStoreRepository,
    ) : ViewModel() {
        private val _wordNoteState = MutableStateFlow<WordNoteState>(WordNoteState.Loading)
        val wordNoteState = _wordNoteState.asStateFlow()

        init {
            loadWords()
        }

        // 처음 단어장 정보 불러오기 api
        fun loadWords() {
            viewModelScope.launch {
                dataStoreRepository.getUserId()?.let { userId ->
                    Log.d("nakyung", "userId: $userId")
                    studyRepository
                        .getWordList(userId)
                        .collectLatest { response ->
                            when (response) {
                                is ApiResponse.Success -> {
                                    Log.d("nakyung", "response: ${response.body}")
                                    _wordNoteState.value =
                                        WordNoteState.Success(
                                            wordList = response.body ?: emptyList(),
                                        )
                                }

                                is ApiResponse.Error -> {
                                    // api 통신 실패
                                    delay(300)
                                    _wordNoteState.value =
                                        WordNoteState.Error(
                                            errorMessage = response.errorMessage,
                                        )
                                }

                                ApiResponse.Init -> {}
                            }
                        }
                } ?: run {
                    _wordNoteState.value = WordNoteState.Error(errorMessage = "User Id is Null")
                }
            }
        }

        fun loadIncorrectWords() {
            viewModelScope.launch {
                dataStoreRepository.getUserId()?.let { userId ->
                    studyRepository.getIncorrectList(userId).collectLatest { response ->
                        when (response) {
                            is ApiResponse.Success -> {
                                _wordNoteState.value =
                                    WordNoteState.Success(
                                        wordList = response.body ?: emptyList(),
                                    )
                            }

                            is ApiResponse.Error -> {
                                // api 통신 실패
                                _wordNoteState.value =
                                    WordNoteState.Error(
                                        errorMessage = response.errorMessage,
                                    )
                            }

                            ApiResponse.Init -> {}
                        }
                    }
                } ?: run {
                    _wordNoteState.value = WordNoteState.Error(errorMessage = "User Id is Null")
                }
            }
        }
    }
