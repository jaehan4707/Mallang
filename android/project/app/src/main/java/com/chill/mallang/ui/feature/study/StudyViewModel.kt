package com.chill.mallang.ui.feature.study

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudyViewModel
    @Inject
    constructor() : ViewModel() {
        private val _studyState = MutableStateFlow<StudyState>(StudyState.Loading)
        val studyState = _studyState.asStateFlow()

        var selectedAnswer by mutableIntStateOf(-1)

        // 퀴즈 데이터 로드
        fun loadQuizData(studyId: Int) {
            viewModelScope.launch {
                if (studyId != -1) {
                    // 풀었던 문제 api 호출 및 통신
                    _studyState.value =
                        StudyState.Success(
                            quizTitle = "풀었던 문제입니당당",
                            quizScript = "우리나라의 경제는 그동안 세계에 유례가 없을 정도로 __ 할만한 성장을 이루었다.",
                            wordList =
                                arrayListOf(
                                    "괄목",
                                    "상대",
                                    "과장",
                                    "시기",
                                ),
                        )
                } else {
                    // 새로운 문제 api 호출 및 통신
                    _studyState.value =
                        StudyState.Success(
                            quizTitle = "빈칸을 채워 주세요",
                            quizScript = "우리나라의 경제는 그동안 세계에 유례가 없을 정도로 ___ 할만한 성장을 이루었다.",
                            wordList =
                                arrayListOf(
                                    "괄목",
                                    "상대",
                                    "과장",
                                    "시기",
                                ),
                        )
                }
            }
        }

        fun selectAnswer(index: Int) {
            selectedAnswer = index + 1
        }

        fun submitQuiz() {
            // 퀴즈 채점 api
        }
    }
