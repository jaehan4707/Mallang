package com.chill.mallang.domain.study.repository;

import com.chill.mallang.domain.study.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value = "SELECT id FROM question WHERE study_game_id = :studyId" ,nativeQuery = true)
    Long findIdByStudyGameId(@Param("studyId") Long studyId);

}
