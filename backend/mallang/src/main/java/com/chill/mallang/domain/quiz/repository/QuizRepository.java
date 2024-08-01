package com.chill.mallang.domain.quiz.repository;

import com.chill.mallang.domain.quiz.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    // 오늘의 퀴즈. id만!
    @Query(value = "SELECT id FROM quiz WHERE DATE_FORMAT(created_at, '%Y-%m-%d') = DATE_FORMAT(CURRENT_DATE, '%Y-%m-%d') AND area = :areaId", nativeQuery = true)
    Long[] findQuizIdByAreaId(@Param("areaId")  Long areaId);
}
