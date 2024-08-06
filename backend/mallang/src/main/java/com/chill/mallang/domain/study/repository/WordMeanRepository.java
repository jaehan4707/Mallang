package com.chill.mallang.domain.study.repository;

import com.chill.mallang.domain.study.model.Word;
import com.chill.mallang.domain.study.model.WordMean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordMeanRepository extends JpaRepository<WordMean, Long> {
    @Query("SELECT w FROM WordMean w WHERE w.id NOT IN (SELECT s.wordMean.id FROM StudyGameLog s WHERE s.user.id = :userId)")
    List<WordMean> findUnusedWordMeansByUserId(Long userId);

    @Query("SELECT w FROM Word w WHERE w.id = :wordId")
    Word findWordByWordId(@Param("wordId") long wordId);
}
