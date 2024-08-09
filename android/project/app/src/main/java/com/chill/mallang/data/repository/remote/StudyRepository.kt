package com.chill.mallang.data.repository.remote

import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.ui.feature.incorrect_word.IncorrectWord
import com.chill.mallang.ui.feature.word.CorrectWord
import kotlinx.coroutines.flow.Flow

interface StudyRepository {
    suspend fun getWordList(userId: Long): Flow<ApiResponse<List<CorrectWord>>>

    suspend fun getIncorrectList(userId: Long): Flow<ApiResponse<List<IncorrectWord>>>
}
