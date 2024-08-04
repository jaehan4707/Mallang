package com.chill.mallang.domain.quiz.repository;

import com.chill.mallang.domain.quiz.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query("SELECT q.id  FROM Quiz q WHERE q.area.id = :areaID ")
    List<Long> getQuizByArea(@Param("areaID") Long areaID);


    @Modifying
    @Transactional
    @Query("UPDATE Quiz q SET q.isUsed = false")
    void resetAllisUsed();

}
