package com.chill.mallang.domain.study.repository;

import com.chill.mallang.domain.study.model.StudyGameLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudyGameLogRepository extends JpaRepository<StudyGameLog, Long>{

    // userid가 userId + result가 1인 데이터만 뽑기
    @Query(value = "SELECT * FROM study_game_log WHERE user_id = :userId AND result = 1" ,nativeQuery = true)
    List<StudyGameLog> getStudyGameLogByUserId(@Param("userId") Long userId);

}
