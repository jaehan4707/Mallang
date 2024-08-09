package com.chill.mallang.data.repositoyimpl.remote

import com.chill.mallang.data.api.QuizApi
import com.chill.mallang.data.model.apiHandler
import com.chill.mallang.data.model.entity.Game01PlayResult
import com.chill.mallang.data.model.entity.Game01QuizData
import com.chill.mallang.data.model.request.FetchGameResultRequest
import com.chill.mallang.data.model.request.GradingUserAnswerRequest
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.remote.QuizRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QuizRepositoryImpl
    @Inject
    constructor(
        private val quizApi: QuizApi,
    ) : QuizRepository {
        override suspend fun getQuizIds(areaId: Long): Flow<ApiResponse<List<Long>>> =
            flow {
                val response =
                    apiHandler {
                        quizApi.getQuizIds(areaId)
                    }
                when (response) {
                    is ApiResponse.Success -> {
                        emit(ApiResponse.Success(response.body?.data))
                    }

                    is ApiResponse.Error -> {
                        emit(
                            ApiResponse.Error(
                                errorCode = response.errorCode,
                                errorMessage = response.errorMessage,
                            ),
                        )
                    }

                    is ApiResponse.Init -> {}
                }
            }

        override suspend fun getQuiz(quizId: Long): Flow<ApiResponse<Game01QuizData>> =
            flow {
                val response =
                    apiHandler {
                        quizApi.getQuiz(quizId)
                    }
                when (response) {
                    is ApiResponse.Success -> {
                        emit(ApiResponse.Success(response.body?.data))
                    }

                    is ApiResponse.Error -> {
                        emit(
                            ApiResponse.Error(
                                errorCode = response.errorCode,
                                errorMessage = response.errorMessage,
                            ),
                        )
                    }

                    is ApiResponse.Init -> {}
                }
            }

        override suspend fun postUserAnswer(gradingUserAnswerRequest: GradingUserAnswerRequest): Flow<ApiResponse<Unit>> =
            flow {
                val response =
                    apiHandler {
                        quizApi.submitUserAnswer(gradingUserAnswerRequest)
                    }
                when (response) {
                    is ApiResponse.Success -> {
                        emit(ApiResponse.Success(response.body))
                    }

                    is ApiResponse.Error -> {
                        emit(
                            ApiResponse.Error(
                                errorCode = response.errorCode,
                                errorMessage = response.errorMessage,
                            ),
                        )
                    }

                    is ApiResponse.Init -> {}
                }
            }

        override suspend fun getResults(fetchGameResultRequest: FetchGameResultRequest): Flow<ApiResponse<Game01PlayResult>> =
            flow {
                val response =
                    apiHandler {
                        quizApi.postFinalDataAndFetchResult(fetchGameResultRequest)
                    }
                when (response) {
                    is ApiResponse.Success -> {
                        emit(ApiResponse.Success(response.body?.data))
                    }

                    is ApiResponse.Error -> {
                        emit(
                            ApiResponse.Error(
                                errorCode = response.errorCode,
                                errorMessage = response.errorMessage,
                            ),
                        )
                    }

                    is ApiResponse.Init -> {}
                }
            }
    }
