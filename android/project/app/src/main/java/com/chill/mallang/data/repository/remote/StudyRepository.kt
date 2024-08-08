package com.chill.mallang.data.repository.remote

import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.ui.feature.word.Word
import kotlinx.coroutines.flow.Flow

interface StudyRepository {
    suspend fun getWordList(userId: Long): Flow<ApiResponse<List<Word.CorrectWord>>>

    suspend fun getIncorrectList(userId: Long): Flow<ApiResponse<List<Word.IncorrectWord>>>
}
