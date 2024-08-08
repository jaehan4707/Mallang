package com.chill.mallang.data.api

import com.chill.mallang.data.model.ResponseBody
import com.chill.mallang.data.model.response.IncorrectWordResponse
import com.chill.mallang.data.model.response.WordResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StudyApi {
    // 단어장 목록 불러오는 api
    @GET("study/studied-word/{userId}")
    suspend fun getWordList(
        @Path("userId") userId: Long,
    ): Response<ResponseBody<List<WordResponse>>>

    // 오답노트 목록 불러오는 api
    @GET("study/wrong-word/all/{userId}")
    suspend fun getIncorrectList(
        @Path("userId") userId: Long,
    ): Response<ResponseBody<List<IncorrectWordResponse>>>
}
