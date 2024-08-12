package com.chill.mallang.ui.feature.game.game01

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chill.mallang.data.model.entity.Game01QuizData
import com.chill.mallang.data.model.entity.User
import com.chill.mallang.data.model.request.FetchGameResultRequest
import com.chill.mallang.data.model.request.GradingUserAnswerRequest
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.remote.QuizRepository
import com.chill.mallang.data.repository.remote.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

private const val TAG = "Game01ViewModel"

@HiltViewModel
class Game01ViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
        private val quizRepository: QuizRepository,
    ) : ViewModel() {
        private val _game01UiEvent = MutableSharedFlow<Game01UiEvent>()
        val gameUiEvent = _game01UiEvent.asSharedFlow()

        // 게임 진행 점령지 ID
        private var _areaId: Long = 0L
        val areaId: Long get() = _areaId

        // 게임01 현재 상태
        var game01State by mutableStateOf(Game01State.INIT)

        // Timer Job
        private var timerJob: Job? = null

        // 게임01 문제 ID List
        private var _questionIdList = mutableListOf<Long>()
        val questionIdList: List<Long> get() = _questionIdList

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

        // 리뷰 UiState
        private var _reviewUiState =
            MutableStateFlow<Game01ReviewUiState>(Game01ReviewUiState.Loading)
        val reviewUiState = _reviewUiState.asStateFlow()

        // 최종 결과 UiState
        private var _resultUiState =
            MutableStateFlow<Game01FinalResultUiState>(Game01FinalResultUiState.Loading)
        val resultUiState = _resultUiState.asStateFlow()

        private val _userInfo = mutableStateOf<User>(User())
        val userInfo: User get() = _userInfo.value

        init {
            fetchUserInfo()
        }

        fun initializeAreaId(areaId: Long) {
            _areaId = areaId
        }

        fun fetchUserInfo() {
            viewModelScope.launch {
                delay(3000L)
                userRepository.getUserInfo().collectLatest { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            _userInfo.value = response.body!!
                            _game01UiEvent.emit(Game01UiEvent.CompleteUserInfoLoad)
                        }

                        is ApiResponse.Error -> {
                            Log.d(TAG, "fetchUserInfo: ${response.errorMessage}")
                        }

                        is ApiResponse.Init -> {
                            Log.d(TAG, "fetchUserInfo: $response")
                        }
                    }
                }
            }
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

        fun updateGame01State(game01State: Game01State) {
            this.game01State = game01State
        }

        fun fetchQuizIds() {
            viewModelScope.launch {
                delay(2000L)
                quizRepository.getQuizIds(areaId).collectLatest { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            _questionIdList.addAll(response.body ?: listOf())
                            _game01UiEvent.emit(Game01UiEvent.CompleteQuizIdsLoad)
                        }

                        is ApiResponse.Error -> {}
                        is ApiResponse.Init -> {}
                    }
                }
            }
        }

        fun fetchQuiz() {
            viewModelScope.launch {
                delay(1000L)
                quizRepository.getQuiz(questionIdList[gameRound - 1]).collectLatest { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            response.body?.let { _questionDataSetList.add(it) }
                            _game01UiEvent.emit(Game01UiEvent.CompleteQuizLoad)
                            _QuizUiState.emit(
                                Game01QUizUiState.Success(
                                    QuizDataSet = response.body!!,
                                ),
                            )
                        }

                        is ApiResponse.Error -> {}
                        is ApiResponse.Init -> {}
                    }
                }
            }
        }

        fun postUserAnswer() {
            pauseTimer()
            updateGame01State(Game01State.ROUND_SUBMIT)

            val quizId = questionIdList[gameRound - 1].toLong()
            val roundPlayingTime = getRoundPlayingTime()
            val userAnswer = userAnswerList[gameRound]
            val currentTimestamp = getCurrentTimestamp()

            viewModelScope.launch {
                delay(1500L)
                quizRepository
                    .postUserAnswer(
                        gradingUserAnswerRequest =
                            GradingUserAnswerRequest(
                                quizId = quizId,
                                userId = userInfo.id,
                                areaId = areaId,
                                userAnswer = userAnswer,
                                answerTime = roundPlayingTime,
                                created_at = currentTimestamp,
                            ),
                    ).collectLatest { response ->
                        when (response) {
                            is ApiResponse.Success -> {
                                _game01UiEvent.emit(Game01UiEvent.CompletePostUserAnswer)
                            }

                            is ApiResponse.Error -> {
                                Log.d(TAG, "postUserAnswer: ${response.errorMessage}")
                            }

                            is ApiResponse.Init -> {}
                        }
                    }
            }
        }

        fun fetchReviews() {
            viewModelScope.launch {
                delay(2500L)
                quizRepository
                    .getResults(
                        fetchGameResultRequest =
                            FetchGameResultRequest(
                                areaId = areaId,
                                userId = userInfo.id,
                                factionId = userInfo.factionId,
                                quizIds = questionIdList,
                            ),
                    ).collectLatest { response ->
                        when (response) {
                            is ApiResponse.Success -> {
                                _reviewUiState.emit(
                                    Game01ReviewUiState.Success(
                                        finalResult = response.body!!,
                                        userAnswerList = userAnswerList,
                                    ),
                                )
                            }

                            is ApiResponse.Error -> {
                                _resultUiState.emit(
                                    Game01FinalResultUiState.Error(
                                        errorMessage = response.errorMessage,
                                    ),
                                )
                            }

                            is ApiResponse.Init -> {}
                        }
                    }
            }
        }

        fun fetchFinalResult() {
            viewModelScope.launch {
                delay(1000L)
                quizRepository
                    .getResults(
                        fetchGameResultRequest =
                            FetchGameResultRequest(
                                areaId = areaId,
                                userId = userInfo.id,
                                factionId = userInfo.factionId,
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
                                _game01UiEvent.emit(Game01UiEvent.CompleteGameResultLoad)
                            }

                            is ApiResponse.Error -> {
                                _resultUiState.emit(
                                    Game01FinalResultUiState.Error(
                                        errorMessage = response.errorMessage,
                                    ),
                                )
                            }

                            is ApiResponse.Init -> {}
                        }
                    }
            }
        }

        fun getCurrentRound(): Int = gameRound

        fun increaseRound() {
            gameRound += 1
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
    REWARD, // 리워드 지급 및 랭크 확인 상태     (사용자가 획득한 경험치를 확인하고, 랭크를 확인하는 단계)
    CURTAIN_CALL, // 커튼콜 상태
}
