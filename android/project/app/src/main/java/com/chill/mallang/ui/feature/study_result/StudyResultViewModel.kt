package com.chill.mallang.ui.feature.study_result

import android.util.Log
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
class StudyResultViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val studyRepository: StudyRepository,
        private val dataStoreRepository: DataStoreRepository,
    ) : ViewModel() {
        private val _studyResultState = MutableStateFlow<StudyResultState>(StudyResultState.Loading)
        val studyResultState = _studyResultState.asStateFlow()

        init {
            Log.d("nakyung", savedStateHandle.get<Long>("studyId").toString())
            gradeQuiz(savedStateHandle.get<Long>("studyId") ?: -1)
        }

        private fun gradeQuiz(studyId: Long) {
            // 퀴즈 결과 불러오기 api 실행
            viewModelScope.launch {
                dataStoreRepository.getUserId()?.let { userId ->
                    studyRepository
                        .getStudyQuizResult(userId = userId, studyId = studyId)
                        .collectLatest { response ->
                            when (response) {
                                is ApiResponse.Success -> {
                                    val data = response.body!!
                                    _studyResultState.value =
                                        StudyResultState.Success(
                                            quizTitle = data.quizTitle,
                                            quizScript = data.quizScript,
                                            wordList = data.wordList,
                                        )
                                }

                                is ApiResponse.Error -> {
                                    _studyResultState.value =
                                        StudyResultState.Error(
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
