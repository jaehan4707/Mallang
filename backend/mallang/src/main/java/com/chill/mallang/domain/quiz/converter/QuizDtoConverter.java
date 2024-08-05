package com.chill.mallang.domain.quiz.converter;

import com.chill.mallang.domain.quiz.dto.response.ResponseQuiz;
import com.chill.mallang.domain.quiz.model.Quiz;

public class QuizDtoConverter {

    public static ResponseQuiz convert(Quiz quiz) {
        return ResponseQuiz.builder()
                .id(quiz.getId())
                .question(quiz.getQuestion())
                .answer(quiz.getAnswer())
                .difficulty(quiz.getDifficulty())
                .build();
    }

}
