package com.chill.mallang.ui.feature.study

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
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
class StudyViewModel
    @Inject
    constructor(
        val savedStateHandle: SavedStateHandle,
        private val studyRepository: StudyRepository,
        private val dataStoreRepository: DataStoreRepository,
    ) : ViewModel() {
        private val _studyState = MutableStateFlow<StudyState>(StudyState.Loading)
        val studyState = _studyState.asStateFlow()

        var selectedAnswer by mutableIntStateOf(-1)

        init {
            loadQuizData(savedStateHandle.get<Long>("studyId") ?: -1)
        }

        // 퀴즈 데이터 로드
        fun loadQuizData(studyId: Long) {
            viewModelScope.launch {
                if (studyId != -1L) {
                    dataStoreRepository.getUserId()?.let { userId ->
                        studyRepository
                            .getIncorrectQuiz(userId = userId, studyId = studyId)
                            .collectLatest { response ->
                                when (response) {
                                    is ApiResponse.Success -> {
                                        val data = response.body!!
                                        _studyState.value =
                                            StudyState.Success(
                                                studyId = data.studyId,
                                                quizTitle = data.quizTitle,
                                                quizScript = data.quizScript,
                                                wordList = data.wordList,
                                            )
                                    }

                                    is ApiResponse.Error -> {
                                        _studyState.value =
                                            StudyState.Error(
                                                errorMessage = response.errorMessage,
                                            )
                                    }

                                    ApiResponse.Init -> {}
                                }
                            }
                    }
                } else {
                    dataStoreRepository.getUserId()?.let { userId ->
                        studyRepository.getStudyQuiz(userId).collectLatest { response ->
                            when (response) {
                                is ApiResponse.Success -> {
                                    val data = response.body!!
                                    _studyState.value =
                                        StudyState.Success(
                                            studyId = data.studyId,
                                            quizTitle = data.quizTitle,
                                            quizScript = data.quizScript,
                                            wordList = data.wordList,
                                        )
                                }

                                is ApiResponse.Error -> {
                                    _studyState.value =
                                        StudyState.Error(
                                            errorMessage = response.errorMessage,
                                        )
                                }

                                ApiResponse.Init -> {}
                            }
                        }
                    }
                }
            }
        }

        fun selectAnswer(index: Int) {
            selectedAnswer = index + 1
        }

        fun submitQuiz(
            studyId: Long,
            answer: Int,
        ) {
            // 퀴즈 채점 api
            viewModelScope.launch {
                dataStoreRepository.getUserId()?.let { userId ->
                    studyRepository
                        .submitStudyAnswer(userId, studyId, answer)
                        .collectLatest { response ->
                            when (response) {
                                is ApiResponse.Success -> {
                                    _studyState.value =
                                        StudyState.SubmitSuccess(
                                            studyId = studyId
                                        )
                                }

                                is ApiResponse.Error -> {
                                    _studyState.value =
                                        StudyState.Error(
                                            errorMessage = response.errorMessage,
                                        )
                                }

                                ApiResponse.Init -> {}
                            }
                        }
                }
            }
        }
    }
