package com.chill.mallang.ui.feature.word

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordNoteViewModel
    @Inject
    constructor() : ViewModel() {
        var state by mutableStateOf(WordNoteState())
            private set

        init {
            loadWords()
        }

        // 처음 단어장 정보 불러오기 api
        fun loadWords() {
            viewModelScope.launch {
                state =
                    state.copy(
                        wordList =
                            arrayListOf(
                                Word.WordCard(
                                    word = "괄목",
                                    meaning = "눈을 비비고 볼 정도로 매우 놀라다.",
                                    example = "우리나라의 경제는 그동안 세계에 유례가 없을 정도로 괄목할 만한 성장을 이루었다.",
                                ),
                                Word.WordCard(
                                    word = "상대",
                                    meaning = "서로 마주 대하다.",
                                    example = "우리나라의 경제는 그동안 세계에 유례가 없을 정도로 괄목할 만한 성장을 이루었다.",
                                ),
                                Word.WordCard(
                                    word = "과장",
                                    meaning = "사실보다 지나치게 불려서 말하거나 행동하다.",
                                    example = "우리나라의 경제는 그동안 세계에 유례가 없을 정도로 괄목할 만한 성장을 이루었다.",
                                ),
                                Word.WordCard(
                                    word = "시기",
                                    meaning = "때나 경우.",
                                    example = "우리나라의 경제는 그동안 세계에 유례가 없을 정도로 괄목할 만한 성장을 이루었다.",
                                ),
                            ),
                    )
            }
        }

        fun loadIncorrectWords() {
            viewModelScope.launch {
                state =
                    state.copy(
                        wordList =
                            arrayListOf(
                                Word.IncorrectWord(
                                    studyId = 1,
                                    word = "우리나라의 경제는 그동안 세계에 유례가 없을 정도로 __할 만한 성장을 이루었다.",
                                    finish = false,
                                ),
                            ),
                    )
            }
        }
    }
