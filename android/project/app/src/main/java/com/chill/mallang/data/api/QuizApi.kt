package com.chill.mallang.data.api

import com.chill.mallang.data.model.ResponseBody
import com.chill.mallang.data.model.entity.Game01PlayResult
import com.chill.mallang.data.model.entity.Game01QuizData
import com.chill.mallang.data.model.request.FetchGameResultRequest
import com.chill.mallang.data.model.request.GradingUserAnswerRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface QuizApi {
    @GET("quiz/start/{areaId}/{userId}")
    suspend fun getQuizIds(
        @Path("areaId") areaId: Long,
        @Path("userId") userId: Long,
    ): Response<ResponseBody<List<Long>>>

    @GET("quiz/{quizId}")
    suspend fun getQuiz(
        @Path("quizId") quizId: Long,
    ): Response<ResponseBody<Game01QuizData>>

    @POST("quiz/submit")
    suspend fun submitUserAnswer(
        @Body gradingUserAnswerRequest: GradingUserAnswerRequest,
    ): Response<Unit>

    @POST("quiz/result")
    suspend fun postFinalDataAndFetchResult(
        @Body fetchGameResultRequest: FetchGameResultRequest,
    ): Response<ResponseBody<Game01PlayResult>>
}
