package com.chill.mallang.data.api

import com.chill.mallang.data.model.ResponseBody
import com.chill.mallang.data.model.response.IncorrectWordResponse
import com.chill.mallang.data.model.response.StudyResponse
import com.chill.mallang.data.model.response.StudyResultResponse
import com.chill.mallang.data.model.response.WordResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StudyApi {
    // 단어장 목록 불러오는 api
    @GET("study/studied-word/{userId}")
    suspend fun getWordList(
        @Path("userId") userId: Long,
    ): Response<ResponseBody<List<WordResponse>>>

    // 오답노트 목록 불러오는 api
    @GET("study/wrong-word/{userId}")
    suspend fun getIncorrectList(
        @Path("userId") userId: Long,
    ): Response<ResponseBody<List<IncorrectWordResponse>>>

    // 틀렸던 문제 하나 요청
    @GET("study/wrong-word/{userID}/{studyId}")
    suspend fun getIncorrectQuiz(
        @Path("userId") userId: Long,
        @Path("studyId") studyId: Long,
    ): Response<ResponseBody<StudyResponse>>

    // 학습 퀴즈 문제 요청
    @GET("study/game/{userId}")
    suspend fun getStudyQuiz(
        @Path("userId") userId: Long,
    ): Response<ResponseBody<StudyResponse>>

    // 학습 퀴즈 문제 최종 요청
    @GET("study/game/{userId}/{studyId}")
    suspend fun getStudyQuizResult(
        @Path("userId") userId: Long,
        @Path("studyId") studyId: Long,
    ): Response<ResponseBody<StudyResultResponse>>

    // 학습 퀴즈 문제 제출
    @POST("study/game/{userId}/{studyId}/{answer}")
    suspend fun submitStudyAnswer(
        @Path("userId") userId: Long,
        @Path("studyId") studyId: Long,
        @Path("answer") answer: Int,
    ): Response<ResponseBody<Boolean>>
}
