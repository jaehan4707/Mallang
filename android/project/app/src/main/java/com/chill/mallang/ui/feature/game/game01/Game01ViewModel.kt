package com.chill.mallang.ui.feature.game.game01

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chill.mallang.data.model.entity.Game01QuizData
import com.chill.mallang.data.model.request.FetchGameResultRequest
import com.chill.mallang.data.model.request.GradingUserAnswerRequest
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.remote.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class Game01ViewModel
    @Inject
    constructor(
        private val quizRepository: QuizRepository,
    ) : ViewModel() {
        // 게임01 현재 상태
        var game01State by mutableStateOf(Game01State.INIT)


        // Timer Job
        private var timerJob: Job? = null

        // 게임01 문제 ID List
        private var _questionIdList = mutableListOf<Int>()
        val questionIdList: List<Int> get() = _questionIdList

        // 게임01 문제 데이터셋 List
        private var _questionDataSetList = mutableListOf<Game01QuizData>()
        val questionDataSetList: List<Game01QuizData> get() = _questionDataSetList

        // 게임01 문제 사용자 답변 List
        private var _userAnswerList =
            mutableStateListOf<String>().apply {
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
        private var _QuizUiState =
            MutableStateFlow<Game01QUizUiState>(Game01QUizUiState.Loading)
        val QuizUiState = _QuizUiState.asStateFlow()

        // 최종 결과 UiState
        private var _resultUiState =
            MutableStateFlow<Game01FinalResultUiState>(Game01FinalResultUiState.Loading)
        val resultUiState = _resultUiState.asStateFlow()

        init {
            loadInitData()
        }

        fun loadInitData() {
            fetchQuizIds()
        }

        fun startGame() {
            fetchQuiz()
        }

        fun completeRoundLoad() {
            resetTimer(ROUND_TIME_LIMIT)
            updateGame01State(Game01State.ROUND_RUNNING)
        }

        fun updateUserAnswer(
            index: Int,
            newAnswer: String,
        ) {
            if (index in _userAnswerList.indices) {
                _userAnswerList[index] = newAnswer
            } else {
                throw IndexOutOfBoundsException()
            }
        }

        private fun updateGame01State(game01State: Game01State) {
            this.game01State = game01State
        }

        private fun fetchQuizIds() {
            viewModelScope.launch {
                quizRepository.getQuizIds(TEST_AREA_ID).collectLatest { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                                _questionIdList.addAll(response.body ?: listOf())
                            updateGame01State(Game01State.ROUND_LOAD)
                            startGame()
                        }
                        is ApiResponse.Error -> {}
                        is ApiResponse.Init -> {}
                    }
                }
            }
        }

        private fun fetchQuiz() {
            viewModelScope.launch {
                quizRepository.getQuiz(questionIdList[gameRound - 1]).collectLatest { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            response.body?.let { _questionDataSetList.add(it) }
                            _QuizUiState.emit(
                                Game01QUizUiState.Success(
                                    QuizDataSet = response.body!!,
                                ),
                            )
                            updateGame01State(Game01State.ROUND_READY)
                        }
                        is ApiResponse.Error -> {}
                        is ApiResponse.Init -> {}
                    }
                }
            }
        }

        fun postUserAnswer() {
            pauseTimer()

            val quizId = questionIdList[gameRound - 1]
            val roundPlayingTime = getRoundPlayingTime()
            val userAnswer = userAnswerList[gameRound]
            val currentTimestamp = getCurrentTimestamp()

            viewModelScope.launch {
                updateGame01State(Game01State.ROUND_SUBMIT)
                quizRepository
                    .postUserAnswer(
                        gradingUserAnswerRequest =
                            GradingUserAnswerRequest(
                                quizId = quizId,
                                userId = testUserId,
                                userAnswer = userAnswer,
                                answerTime = roundPlayingTime,
                                created_at = currentTimestamp,
                            ),
                    ).collectLatest { response ->
                        when (response) {
                            is ApiResponse.Success -> {
                                if (gameRound == ROUND_COUNT) {
                                    updateGame01State(Game01State.FINISH)
                                } else {
                                    gameRound += 1
                                    updateGame01State(Game01State.ROUND_LOAD)
                                    fetchQuiz()
                                }
                            }
                            is ApiResponse.Error -> {}
                            is ApiResponse.Init -> {}
                        }
                    }
            }
        }

        fun fetchFinalResult() {
            viewModelScope.launch {
                quizRepository
                    .getResults(
                        fetchGameResultRequest =
                            FetchGameResultRequest(
                                userId = testUserId,
                                factionId = testFactionId,
                                areaId = TEST_AREA_ID,
                                quizIds = questionIdList,
                            ),
                    ).collectLatest { response ->
                        when (response) {
                            is ApiResponse.Success -> {
                                _resultUiState.emit(
                                    Game01FinalResultUiState.Success(
                                        finalResult = response.body!!,
                                    ),
                                )
                                if (gameRound == ROUND_COUNT) {
                                    updateGame01State(Game01State.FINISH)
                                } else {
                                    gameRound += 1
                                    updateGame01State(Game01State.ROUND_READY)
                                }
                            }
                            is ApiResponse.Error -> {}
                            is ApiResponse.Init -> {}
                        }
                    }
            }
        }

        fun startTimer() {
            timerJob?.cancel()
            timerJob =
                viewModelScope.launch {
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

        private fun getRoundPlayingTime(): Int = ROUND_TIME_LIMIT - remainingTime.value

        private fun getCurrentTimestamp(): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            return sdf.format(Date())
        }

        companion object Game01Constants {
            const val ROUND_TIME_LIMIT = 100
            const val ROUND_COUNT = 3

            // 더미 데이터
            const val TEST_AREA_ID = 1
        }
    }

enum class Game01State {
    INIT, // 게임 준비 상태         (게임에 필요한 퀴즈 데이터 불러오는 단계)
    ROUND_LOAD, // 라운드 로드 상태       (라운드에 필요한 데이터를 불러오는 단계)
    ROUND_READY, // 라운드 준비 상태       (라운드 시작 전에 현재 라운드를 사용자에게 알리는 단계)
    ROUND_RUNNING, // 라운드 진행 상태       (라운드 진행 중인 단계)
    ROUND_SUBMIT, // 라운드 답안 제출 상태   (라운드에서 작성한 사용자 답안을 서버에 POST 하는 단계)
    ROUND_DONE, // 라운드 완료 상태       (답안 제출 및 채점이 완료된 단계)
    REVIEW, // 게임 결과 리뷰 상태       (게임 결과를 사용자가 확인하고, 채점 결과를 리뷰하는 단계)
    FINISH, // 게임 완료 상태         (사용자가 게임을 완료하고, 사용자에게 결과를 알리는 단계)
}
