package com.chill.mallang.domain.quiz.repository;

import com.chill.mallang.domain.quiz.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
