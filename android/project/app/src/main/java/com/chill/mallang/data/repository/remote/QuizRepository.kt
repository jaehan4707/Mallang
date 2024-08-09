package com.chill.mallang.data.repository.remote

import com.chill.mallang.data.model.entity.Game01PlayResult
import com.chill.mallang.data.model.entity.Game01QuizData
import com.chill.mallang.data.model.request.FetchGameResultRequest
import com.chill.mallang.data.model.request.GradingUserAnswerRequest
import com.chill.mallang.data.model.response.ApiResponse
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    suspend fun getQuizIds(areaId: Long): Flow<ApiResponse<List<Long>>>

    suspend fun getQuiz(quizId: Long): Flow<ApiResponse<Game01QuizData>>

    suspend fun postUserAnswer(gradingUserAnswerRequest: GradingUserAnswerRequest): Flow<ApiResponse<Unit>>

    suspend fun getResults(fetchGameResultRequest: FetchGameResultRequest): Flow<ApiResponse<Game01PlayResult>>
}
