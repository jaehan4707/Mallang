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

        fun loadIncorrectWords() {
            viewModelScope.launch {
                dataStoreRepository.getUserId()?.let { userId ->
                    studyRepository.getIncorrectList(userId).collectLatest { response ->
                        when (response) {
                            is ApiResponse.Success -> {
                                _incorrectNoteState.value =
                                    IncorrectNoteState.Success(
                                        wordList = response.body ?: emptyList(),
                                    )
                            }

                            is ApiResponse.Error -> {
                                // api 통신 실패
                                _incorrectNoteState.value =
                                    IncorrectNoteState.Error(
                                        errorMessage = response.errorMessage,
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
