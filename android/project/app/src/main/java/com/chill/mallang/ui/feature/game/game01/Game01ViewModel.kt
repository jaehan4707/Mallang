package com.chill.mallang.ui.feature.game.game01

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chill.mallang.ui.feature.fort_detail.UserRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class Game01ViewModel @Inject constructor(

) : ViewModel() {
    // 게임01 현재 상태
    var game01State by mutableStateOf(Game01State.INIT)

    // 게임01 라운드 제한 시간
    val ROUND_TIME_LIMIT = 10

    // 게임01 라운드 수
    val ROUND_COUNT = 3

    // Timer Job
    private var timerJob: Job? = null

    // 게임01 문제 ID List
    private var _questionIdList = mutableStateListOf<Int>()
    val questionIdList: List<Int> get() = _questionIdList

    // 게임01 문제 데이터셋 List
    private var _questionDataSetList = mutableStateListOf<QuestionDataSet>()
    val questionDataSetList: List<QuestionDataSet> get() = _questionDataSetList

    // 게임01 문제 사용자 답변 List
    private var _userAnswerList = mutableStateListOf<String>().apply {
        addAll(List(ROUND_COUNT + 1) { "" })
    }
    val userAnswerList: List<String> get() = _userAnswerList

    // 게임01 현재 라운드
    var gameRound by mutableStateOf(1)
        private set

    // 게임01 현재 라운드 잔여 시간
    private val _remainingTime = MutableStateFlow(0)
    val remainingTime: StateFlow<Int> get() = _remainingTime

    // 게임 로딩 결과 UiState
    private var _loadQuizsState = MutableStateFlow<Game01LoadQUizsUiState>(Game01LoadQUizsUiState.Loading)
    val loadQuizsState = _loadQuizsState.asStateFlow()

    // 최종 결과 UiState
    private var _resultUiState = MutableStateFlow<Game01FinalResultUiState>(Game01FinalResultUiState.Loading)
    val resultUiState = _resultUiState.asStateFlow()

    init {
        startGame01()
    }

    fun startGame01() {
        fetchQuestionDataSet()
        updateGame01State(Game01State.ROUND_READY)
    }

    fun completeRoundLoad() {
        resetTimer(ROUND_TIME_LIMIT)
        updateGame01State(Game01State.ROUND_RUNNING)
    }

    fun updateUserAnswer(index: Int, newAnswer: String) {
        if (index in _userAnswerList.indices) {
            _userAnswerList[index] = newAnswer
        } else {
            throw IndexOutOfBoundsException()
        }
    }

    private fun updateGame01State(game01State: Game01State) {
        this.game01State = game01State
    }

    private fun fetchQuestionDataSet() {
        viewModelScope.launch {
            try {
                // TODO : 점령 시작을 위한 QUIZ ID 조회 API 호출
                _questionIdList.addAll(testIdList)

                // TODO: 퀴즈 정보 상세 조회 API 호출
                _questionDataSetList.addAll(testDataSetList)
            } catch (e: Exception) {
                // TODO : 에러 처리
            }
        }
    }

    fun fetchFinalResult() {
        viewModelScope.launch {
            viewModelScope.launch {
                _resultUiState.value = Game01FinalResultUiState.Loading
                try {
                    _resultUiState.emit(testGame01FinalResultUiState)
                } catch (e: Exception) {
                    // TODO : 에러 처리
                }
            }
        }
    }

    fun postUserAnswer() {
        pauseTimer()

        // TODO : PostUserAnswer Request 생성
        val roundPlayingTime = getRoundPlayingTime()
        val userAnswer = userAnswerList[gameRound]
        val currentTimestamp = getCurrentTimestamp()

        viewModelScope.launch {
            updateGame01State(Game01State.ROUND_SUBMIT)
            delay(5000L)
            updateGame01State(Game01State.ROUND_DONE)
            delay(1000L)

            if (gameRound == ROUND_COUNT) {
                updateGame01State(Game01State.FINISH)
            } else {
                gameRound += 1
                updateGame01State(Game01State.ROUND_READY)
            }
        }
    }

    fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_remainingTime.value!! > 0) {
                delay(1000)
                _remainingTime.value -= 1
            }
        }
    }

    private fun pauseTimer() {
        timerJob?.cancel()
    }

    private fun resetTimer(initialTime: Int) {
        _remainingTime.value = initialTime
    }

    private fun getRoundPlayingTime(): Int {
        return ROUND_TIME_LIMIT - remainingTime.value
    }

    private fun getCurrentTimestamp(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        return sdf.format(Date())
    }

    companion object {
        // 더미 데이터
        val testGame01FinalResultUiState: Game01FinalResultUiState =
            Game01FinalResultUiState.Success(
                PlayResult(
                    UserPlayResult(
                        rank = 1,
                        score = listOf(100, 100, 100),
                        totalScore = 300,
                    ),
                    TeamPlayResult(
                        myTeamRankList = listOf(
                            UserRecord(1, 3, "말팀01", 290, 100),
                            UserRecord(2, 4, "말팀02", 280, 100),
                        ),
                        myTeamTotalScore = 1000,
                        oppoTeamTotalScore = 900,
                    )
                )
            )

        val testIdList = listOf(1, 2, 3)

        val testDataSetList = listOf(
            QuestionDataSet(question = "문제 1이요", answer = "정답1 이요", difficulty = 1, type = 1),
            QuestionDataSet(question = "문제 2이요", answer = "정답2 이요", difficulty = 2, type = 2),
            QuestionDataSet(question = "문제 3이요", answer = "정답3 이요", difficulty = 3, type = 3),
        )
    }
}

enum class Game01State {
    INIT,               // 게임 준비 상태         (게임에 필요한 퀴즈 데이터 불러오는 단계)
    ROUND_READY,        // 라운드 준비 상태       (라운드 시작 전에 현재 라운드를 사용자에게 알리는 단계)
    ROUND_RUNNING,      // 라운드 진행 상태       (라운드 진행 중인 단계)
    ROUND_SUBMIT,       // 라운드 답안 제출 상태   (라운드에서 작성한 사용자 답안을 서버에 POST 하는 단계)
    ROUND_DONE,         // 라운드 완료 상태       (답안 제출 및 채점이 완료된 단계)
    FINISH,             // 게임 완료 상태         (사용자가 게임을 완료하고, 사용자에게 결과를 알리는 단계)
}
