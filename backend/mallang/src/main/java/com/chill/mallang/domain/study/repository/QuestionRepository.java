package com.chill.mallang.domain.study.repository;

import com.chill.mallang.domain.study.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Long findIdByStudyId(@Param("studyId") Long studyId);

}
