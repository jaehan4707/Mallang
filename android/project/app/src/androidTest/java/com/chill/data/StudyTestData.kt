package com.chill.data

import com.chill.mallang.data.model.entity.StudyQuiz

object StudyTestData {
    val studyQuiz = StudyQuiz(
        studyId = -1L,
        quizTitle = "빈칸을 채워주세요",
        quizScript = "그는 학업에서 항상 나의 라이벌이다",
        wordList = listOf("관찰자", "라이벌", "조력자", "추종자")
    )
    val inCorrectQuiz = StudyQuiz(
        studyId = 1L,
        quizTitle = "빈칸을 채워주세요",
        quizScript = "그는 학업에서 항상 나의 라이벌이다",
        wordList = listOf("관찰자", "라이벌", "조력자", "추종자")
    )

    val submitErrorMessage = "제출에 실패했습니다."
    val loadQuizDataErrorMessage = "데이터 로딩에 실패했습니다."
}