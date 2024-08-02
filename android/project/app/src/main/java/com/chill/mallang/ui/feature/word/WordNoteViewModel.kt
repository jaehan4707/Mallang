package com.chill.mallang.ui.feature.word

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class WordNoteViewModel : ViewModel() {
    var state by mutableStateOf(WordNoteState())
        private set

    init {
        loadWords()
    }

    // 처음 단어장 정보 불러오기 api
    private fun loadWords() {
        viewModelScope.launch {
            state =
                state.copy(
                    wordList =
                        arrayListOf(
                            WordCard(
                                word = "괄목",
                                meaning = "눈을 비비고 볼 정도로 매우 놀라다.",
                                example = "우리나라의 경제는 그동안 세계에 유례가 없을 정도로 괄목할 만한 성장을 이루었다.",
                            ),
                            WordCard(
                                word = "상대",
                                meaning = "서로 마주 대하다.",
                                example = "우리나라의 경제는 그동안 세계에 유례가 없을 정도로 괄목할 만한 성장을 이루었다.",
                            ),
                            WordCard(
                                word = "과장",
                                meaning = "사실보다 지나치게 불려서 말하거나 행동하다.",
                                example = "우리나라의 경제는 그동안 세계에 유례가 없을 정도로 괄목할 만한 성장을 이루었다.",
                            ),
                            WordCard(
                                word = "시기",
                                meaning = "때나 경우.",
                                example = "우리나라의 경제는 그동안 세계에 유례가 없을 정도로 괄목할 만한 성장을 이루었다.",
                            ),
                        ),
                    incorrectList =
                        arrayListOf(
                            IncorrectWord(
                                word = "괄목",
                                quizTitle = "빈칸을 채워 주세요",
                                quizScript = "우리나라의 경제는 그동안 세계에 유례가 없을 정도로 괄목할만한 성장을 이루었다.",
                                wordList =
                                    arrayListOf(
                                        "괄목" to "눈을 비비고 볼 정도로 매우 놀라다.",
                                        "상대" to "서로 마주 대하다.",
                                        "과장" to "사실보다 지나치게 불려서 말하거나 표현하다.",
                                        "시기" to "때나 경우.",
                                    ),
                                userAnswer = 2,
                                systemAnswer = 1, // 임시 정답
                            ),
                        ),
                )
        }
    }
}
