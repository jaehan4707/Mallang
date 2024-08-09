package com.chill.mallang.ui.feature.incorrect_word

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
class IncorrectWordViewModel
    @Inject
    constructor(
        private val studyRepository: StudyRepository,
        private val dataStoreRepository: DataStoreRepository,
    ) : ViewModel() {
        private val _incorrectNoteState =
            MutableStateFlow<IncorrectNoteState>(IncorrectNoteState.Loading)
        val incorrectNoteState = _incorrectNoteState.asStateFlow()

        init {
            loadIncorrectWords()
        }

        private fun loadIncorrectWords() {
            viewModelScope.launch {
                dataStoreRepository.getUserId()?.let { userId ->
                    studyRepository.getIncorrectList(userId).collectLatest { response ->
                        when (response) {
                            is ApiResponse.Success -> {
//                                _wordNoteState.value =
//                                    WordNoteState.Success(
//                                        wordList = response.body ?: emptyList(),
//                                    )
                            }

                            is ApiResponse.Error -> {
                                // api 통신 실패
//                                _incorrectNoteState.value =
//                                    IncorrectNoteState.Error(
//                                        errorMessage = response.errorMessage,
//                                    )
                                // 테스트를 위한 더미 데이터
                                _incorrectNoteState.value =
                                    IncorrectNoteState.Success(
                                        wordList =
                                            arrayListOf(
                                                IncorrectWord(
                                                    studyId = 1,
                                                    script = "틀린 문제의 스크립트 __ 여기에 있어요 1",
                                                ),
                                                IncorrectWord(
                                                    studyId = 2,
                                                    script = "틀린 문제의 스크립트 __ 여기에 있어요 2",
                                                ),
                                            ),
                                    )
                            }

                            ApiResponse.Init -> {}
                        }
                    }
                } ?: run {
                    _incorrectNoteState.value =
                        IncorrectNoteState.Error(errorMessage = "User Id is Null")
                }
            }
        }
    }
