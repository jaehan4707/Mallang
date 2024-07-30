package com.chill.mallang.domain.quiz.repository;

import com.chill.mallang.domain.area.model.AreaLog;
import com.chill.mallang.domain.quiz.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByAreaId(Long areaId);
}
