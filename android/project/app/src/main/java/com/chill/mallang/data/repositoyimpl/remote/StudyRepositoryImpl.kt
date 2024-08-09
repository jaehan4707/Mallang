package com.chill.mallang.data.repositoyimpl.remote

import com.chill.mallang.data.api.StudyApi
import com.chill.mallang.data.model.apiHandler
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.remote.StudyRepository
import com.chill.mallang.ui.feature.word.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StudyRepositoryImpl
    @Inject
    constructor(
        private val studyApi: StudyApi,
    ) : StudyRepository {
        override suspend fun getWordList(userId: Long): Flow<ApiResponse<List<Word.CorrectWord>>> =
            flow {
                val response =
                    apiHandler {
                        studyApi.getWordList(userId = userId)
                    }
                when (response) {
                    is ApiResponse.Success -> {
                        emit(
                            ApiResponse.Success(
                                response.body?.data?.map {
                                    Word.CorrectWord(
                                        word = it.word ?: "",
                                        pos = it.pos ?: "",
                                        example = it.example ?: "",
                                        meaning = it.meaning ?: "",
                                    )
                                },
                            ),
                        )
                    }

                    is ApiResponse.Error -> {
                        emit(
                            ApiResponse.Error(
                                errorCode = response.errorCode,
                                errorMessage = response.errorMessage,
                            ),
                        )
                    }

                    ApiResponse.Init -> {
                        emit(ApiResponse.Init)
                    }
                }
            }

        override suspend fun getIncorrectList(userId: Long): Flow<ApiResponse<List<Word.IncorrectWord>>> =
            flow {
                val response =
                    apiHandler {
                        studyApi.getIncorrectList(userId = userId)
                    }
                when (response) {
                    is ApiResponse.Success -> {
                        emit(
                            ApiResponse.Success(
                                response.body?.data?.map {
                                    Word.IncorrectWord(
                                        studyId = it.studyId ?: -1,
                                        word = it.word ?: "",
                                    )
                                },
                            ),
                        )
                    }

                    is ApiResponse.Error -> {
                        emit(
                            ApiResponse.Error(
                                errorCode = response.errorCode,
                                errorMessage = response.errorMessage,
                            ),
                        )
                    }

                    is ApiResponse.Init -> {
                        emit(ApiResponse.Init)
                    }
                }
            }
    }
