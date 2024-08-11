package com.chill.mallang.data.repository.remote

import com.chill.mallang.data.model.entity.StudyQuiz
import com.chill.mallang.data.model.entity.StudyQuizResult
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.ui.feature.incorrect_word.IncorrectWord
import com.chill.mallang.ui.feature.word.CorrectWord
import kotlinx.coroutines.flow.Flow

interface StudyRepository {
    // 단어장 목록 불러오기
    suspend fun getWordList(userId: Long): Flow<ApiResponse<List<CorrectWord>>>

    // 오답노트 목록 불러오기
    suspend fun getIncorrectList(userId: Long): Flow<ApiResponse<List<IncorrectWord>>>

    // 학습 퀴즈 불러오기 (새로운 문제)
    suspend fun getStudyQuiz(userId: Long): Flow<ApiResponse<StudyQuiz>>

    // 틀렸던 문제 불러오기
    suspend fun getIncorrectQuiz(
        userId: Long,
        studyId: Long,
    ): Flow<ApiResponse<StudyQuiz>>

    // 학습 퀴즈 최종 요청
    suspend fun getStudyQuizResult(
        userId: Long,
        studyId: Long,
    ): Flow<ApiResponse<StudyQuizResult>>

    // 학습 퀴즈 정답 제출
    suspend fun submitStudyAnswer(
        userId: Long,
        studyId: Long,
        answer: Int,
    ): Flow<ApiResponse<Unit>>
}
