package com.chill.mallang.domain.study.repository;

import com.chill.mallang.domain.study.model.StudyGame;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudyGameRepository extends JpaRepository<StudyGame, Long> {
    @Query(value = "SELECT * FROM study_game WHERE word_mean_id = :wordMeanId", nativeQuery = true )
    StudyGame findByWordMeanId(@Param("wordMeanId") Long wordMeanId);

    Optional<StudyGame> findById(Long id);
}
