package com.chill.mallang.ui.feature.quiz

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {
    var state by mutableStateOf(QuizState())
        private set

    init {
        loadQuizData()
    }

    // 퀴즈 데이터 로드
    private fun loadQuizData() {
        viewModelScope.launch {
            // api 호출 및 통신
            state = state.copy(
                quizTitle = "빈칸을 채워 주세요",
                quizScript = "우리나라의 경제는 그동안 세계에 유례가 없을 정도로 ___ 할만한 성장을 이루었다.",
                wordList = arrayListOf(
                    "괄목",
                    "상대",
                    "과장",
                    "시기"
                )
            )
        }
    }

    fun selectAnswer(index: Int) {
        state = state.copy(selectedAnswer = index)
    }

    fun submitQuiz() {
        // 퀴즈 채점 api
    }
}