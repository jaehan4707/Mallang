package com.chill.mallang.domain.study.repository;

import com.chill.mallang.domain.study.model.StudyGame;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyGameRepository extends JpaRepository<StudyGame, Long> {
    @Query("SELECT s FROM StudyGame s WHERE s.wordMean.id = :wordMeanId")
    StudyGame findByWordMeanId(@Param("wordMeanId") Long wordMeanId);
}
