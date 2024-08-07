package com.chill.mallang.domain.study.repository;

import com.chill.mallang.domain.study.model.Word;
import com.chill.mallang.domain.study.model.WordMean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WordMeanRepository extends JpaRepository<WordMean, Long> {
    @Query(value ="SELECT * FROM word_mean WHERE id NOT IN (SELECT word_mean_id FROM study_game_log WHERE user_id = :userId) ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<WordMean> findUnusedWordMeansByUserId(Long userId);
}
