package com.chill.mallang.ui.feature.word

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.local.DataStoreRepository
import com.chill.mallang.data.repository.remote.StudyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
//                                    _wordNoteState.value =
//                                        WordNoteState.Success(
//                                            wordList = response.body ?: emptyList(),
//                                        )
                                    // 테스트용 더미데이터 처리
                                    _wordNoteState.value =
                                        WordNoteState.Success(
                                            wordList = arrayListOf(
                                                Word.CorrectWord(
                                                    word = "단어 1",
                                                    pos = "명사",
                                                    meaning = "여기는 단어의 뜻이 들어가 있어요.",
                                                    example = "여기는 단어를 사용한 문장이 들어가 있어요."
                                                ),
                                                Word.CorrectWord(
                                                    word = "단어 2",
                                                    pos = "동사",
                                                    meaning = "여기는 단어의 뜻이 들어가 있어요.",
                                                    example = "여기는 단어를 사용한 문장이 들어가 있어요."
                                                ),
                                                Word.CorrectWord(
                                                    word = "단어 3",
                                                    pos = "형용사",
                                                    meaning = "여기는 단어의 뜻이 들어가 있어요.",
                                                    example = "여기는 단어를 사용한 문장이 들어가 있어요."
                                                ),
                                                Word.CorrectWord(
                                                    word = "단어 4",
                                                    pos = "부사",
                                                    meaning = "여기는 단어의 뜻이 들어가 있어요.",
                                                    example = "여기는 단어를 사용한 문장이 들어가 있어요."
                                                )
                                            )
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

        fun loadIncorrectWords() {
            viewModelScope.launch {
                dataStoreRepository.getUserId()?.let { userId ->
                    studyRepository.getIncorrectList(userId).collectLatest { response ->
                        when (response) {
                            is ApiResponse.Success -> {
//                                _wordNoteState.value =
//                                    WordNoteState.Success(
//                                        wordList = response.body ?: emptyList(),
//                                    )
                                // 테스트를 위한 더미 데이터
                                _wordNoteState.value =
                                    WordNoteState.Success(
                                        wordList = arrayListOf(
                                            Word.IncorrectWord(
                                                studyId = 1,
                                                word = "틀린 문제의 스크립트 __ 여기에 있어요 1"
                                            ),
                                            Word.IncorrectWord(
                                                studyId = 2,
                                                word = "틀린 문제의 스크립트 __ 여기에 있어요 2"
                                            )
                                        )
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
