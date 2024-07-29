package com.chill.mallang.domain.quiz.service;
import org.springframework.http.ResponseEntity;

public interface QuizService {
    void createQuizFromPrompt();
    String checkScore();
    void checkModels();

    ResponseEntity<?> getById(Long quizID);
}
