package com.chill.mallang.data.repositoyimpl.remote

import com.chill.mallang.data.api.StudyApi
import com.chill.mallang.data.model.apiHandler
import com.chill.mallang.data.model.entity.StudyQuiz
import com.chill.mallang.data.model.entity.StudyQuizResult
import com.chill.mallang.data.model.entity.StudyResultWord
import com.chill.mallang.data.model.response.ApiResponse
import com.chill.mallang.data.repository.remote.StudyRepository
import com.chill.mallang.ui.feature.incorrect_word.IncorrectWord
import com.chill.mallang.ui.feature.word.CorrectWord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StudyRepositoryImpl
    @Inject
    constructor(
        private val studyApi: StudyApi,
    ) : StudyRepository {
        override suspend fun getWordList(userId: Long): Flow<ApiResponse<List<CorrectWord>>> =
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
                                    CorrectWord(
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

        override suspend fun getIncorrectList(userId: Long): Flow<ApiResponse<List<IncorrectWord>>> =
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
                                    IncorrectWord(
                                        studyId = it.studyId ?: -1,
                                        script = it.script ?: "",
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

        override suspend fun getStudyQuiz(userId: Long): Flow<ApiResponse<StudyQuiz>> =
            flow {
                val response =
                    apiHandler {
                        studyApi.getStudyQuiz(userId = userId)
                    }
                when (response) {
                    is ApiResponse.Success -> {
                        emit(
                            ApiResponse.Success(
                                response.body?.data?.let {
                                    StudyQuiz(
                                        studyId = it.studyId ?: -1,
                                        quizTitle = it.quizTitle ?: "",
                                        quizScript = it.quizScript ?: "",
                                        wordList = it.wordList ?: emptyList(),
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

        override suspend fun getIncorrectQuiz(
            userId: Long,
            studyId: Long,
        ): Flow<ApiResponse<StudyQuiz>> =
            flow {
                val response =
                    apiHandler {
                        studyApi.getIncorrectQuiz(userId = userId, studyId = studyId)
                    }
                when (response) {
                    is ApiResponse.Success -> {
                        emit(
                            ApiResponse.Success(
                                response.body?.data?.let {
                                    StudyQuiz(
                                        studyId = it.studyId ?: -1,
                                        quizTitle = it.quizTitle ?: "",
                                        quizScript = it.quizScript ?: "",
                                        wordList = it.wordList ?: emptyList(),
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

        override suspend fun getStudyQuizResult(
            userId: Long,
            studyId: Long,
        ): Flow<ApiResponse<StudyQuizResult>> =
            flow {
                val response =
                    apiHandler {
                        studyApi.getStudyQuizResult(userId = userId, studyId = studyId)
                    }
                when (response) {
                    is ApiResponse.Success -> {
                        emit(
                            ApiResponse.Success(
                                response.body?.data?.let {
                                    val wordList =
                                        it.wordList?.map { result ->
                                            StudyResultWord(
                                                word = result.word ?: "",
                                                meaning = result.meaning ?: "",
                                            )
                                        }
                                    StudyQuizResult(
                                        quizTitle = it.quizTitle ?: "",
                                        quizScript = it.quizScript ?: "",
                                        wordList = wordList ?: emptyList(),
                                        result = it.result ?: false,
                                        systemAnswer = it.systemAnswer ?: -1,
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

        override suspend fun submitStudyAnswer(
            userId: Long,
            studyId: Long,
            answer: Int,
        ): Flow<ApiResponse<Unit>> =
            flow {
                val response =
                    apiHandler {
                        studyApi.submitStudyAnswer(userId = userId, studyId = studyId, answer = answer)
                    }
                when (response) {
                    is ApiResponse.Success -> {
                        emit(
                            ApiResponse.Success(
                                response.body?.data?.let {

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
