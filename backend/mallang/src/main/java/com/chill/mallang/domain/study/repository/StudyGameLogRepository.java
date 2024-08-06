package com.chill.mallang.domain.study.repository;

import com.chill.mallang.domain.study.model.StudyGameLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudyGameLogRepository extends JpaRepository<StudyGameLog, Long>{

    // user_id = userId + result가 1인 데이터만 뽑기
    // 맞힌 문제 단어 뽑기
    @Query(value = "SELECT * FROM study_game_log WHERE user_id = :userId AND result = 1" ,nativeQuery = true)
    List<StudyGameLog> getStudyGameLogByUserId(@Param("userId") Long userId);

    // user_id = userId + result가 0인 데이터만 뽑기
    // 오답노트 만들기
    @Query(value = "SELECT * FROM study_game_log WHERE user_id = :userId AND result = 0" ,nativeQuery = true)
    List<StudyGameLog> getWrongStudyGameLogByUserId(Long userId);

}
