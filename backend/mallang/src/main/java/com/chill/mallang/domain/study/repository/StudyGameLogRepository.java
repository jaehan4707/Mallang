package com.chill.mallang.domain.study.repository;

import com.chill.mallang.domain.study.model.StudyGameLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyGameLogRepository extends JpaRepository<StudyGameLog, Long>{

    // user_id = userId + result가 1인 데이터만 뽑기
    // 맞힌 문제 단어 뽑기
    @Query(value = "SELECT * FROM study_game_log WHERE user_id = :userId AND result = 1" ,nativeQuery = true)
    List<StudyGameLog> getStudyGameLogByUserId(@Param("userId") Long userId);

    // user_id = userId + result가 0인 데이터만 뽑기
    // 오답노트 만들기
    @Query(value = "SELECT * FROM study_game_log WHERE user_id = :userId AND result = 0" ,nativeQuery = true)
    List<StudyGameLog> getWrongStudyGameLogByUserId(@Param("userId") Long userId);

    //(user_id = userId) + (study_game_id = studyId) + result = 0인 데이터
    @Query(value = "SELECT * FROM study_game_log WHERE user_id = :userId AND study_game_id = :studyId AND result = 0" ,nativeQuery = true)
    Optional<StudyGameLog> getOneWrongStudyGameLogByUserIdStudyId(@Param("userId") Long userId, @Param("studyId") Long studyId);

    //이미 푼 문제인지 아닌지 확인 용
    @Query(value = "SELECT * FROM study_game_log WHERE user_id = :userId AND study_game_id = :studyId FOR UPDATE", nativeQuery = true)
    Optional<StudyGameLog> findByStudyGameAndUserForUpdate(@Param("userId") Long userId, @Param("studyId") Long studyId);
}
