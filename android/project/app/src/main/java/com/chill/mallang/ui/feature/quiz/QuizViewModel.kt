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
                quizWord = "괄목",
                wordList = arrayListOf(
                    "괄목" to "눈을 비비고 볼 정도로 매우 놀라다.",
                    "상대" to "서로 마주 대하다.",
                    "과장" to "사실보다 지나치게 불려서 말하거나 표현하다.",
                    "시기" to "때나 경우."
                )
            )
        }
    }

    fun selectAnswer(index: Int) {
        if (!state.isResultScreen) {
            state = state.copy(selectedAnswer = index)
        } else {
            state = state.copy(expandedAnswer = index)
        }
    }

    fun submitQuiz() {
        // 퀴즈 제출 로직
        state = state.copy(
            isResultScreen = true,
            systemAnswer = 1, // 임시로 1
            isAnswer = 1 == state.selectedAnswer
        )
    }
}