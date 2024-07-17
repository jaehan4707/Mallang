package com.chill.mallang.domain.quiz.service;

import com.chill.mallang.domain.quiz.dto.QuizDto;

public interface QuizService {
    QuizDto createQuizFromPrompt();
    void checkModels();
}
