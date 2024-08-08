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

    // user_id = userId + result가 1인 가장 최신의 데이터만 뽑기
    // 맞힌 문제 단어 뽑기
    @Query(value = "SELECT sgl.* " +
            "FROM study_game_log sgl " +
            "JOIN ( " +
            "    SELECT user_id, study_game_id, MAX(created_at) as max_created_at " +
            "    FROM study_game_log " +
            "    WHERE user_id = :userId AND result = 1 " +
            "    GROUP BY user_id, study_game_id " +
            ") latest " +
            "ON sgl.user_id = latest.user_id " +
            "AND sgl.study_game_id = latest.study_game_id " +
            "AND sgl.created_at = latest.max_created_at " +
            "WHERE sgl.user_id = :userId AND sgl.result = 1", nativeQuery = true)
    List<StudyGameLog> getStudyGameLogByUserId(@Param("userId") Long userId);

    // user_id = userId + result가 0인 '가장 최신의 데이터'만 뽑기
    // 오답노트 만들기
    @Query(value = "SELECT sgl.* " +
            "FROM study_game_log sgl " +
            "JOIN ( " +
            "    SELECT user_id, study_game_id, MAX(created_at) as max_created_at " +
            "    FROM study_game_log " +
            "    WHERE user_id = :userId AND result = 0 " +
            "    GROUP BY user_id, study_game_id " +
            ") latest " +
            "ON sgl.user_id = latest.user_id " +
            "AND sgl.study_game_id = latest.study_game_id " +
            "AND sgl.created_at = latest.max_created_at " +
            "WHERE sgl.user_id = :userId AND sgl.result = 0", nativeQuery = true)
    List<StudyGameLog> getWrongStudyGameLogByUserId(@Param("userId") Long userId);

    //(user_id = userId) + (study_game_id = studyId) + result = 0인 가장 최신의 데이터
    @Query(value = "SELECT * FROM study_game_log WHERE user_id = :userId AND study_game_id = :studyId AND result = 0 ORDER BY id DESC LIMIT 1 FOR UPDATE" ,nativeQuery = true)
    Optional<StudyGameLog> getOneWrongStudyGameLogByUserIdStudyId(@Param("userId") Long userId, @Param("studyId") Long studyId);

    //이미 푼 문제인지 아닌지 확인 용
    @Query(value = "SELECT * FROM study_game_log WHERE user_id = :userId AND study_game_id = :studyId ORDER BY id DESC LIMIT 1 FOR UPDATE", nativeQuery = true)
    Optional<StudyGameLog> findByStudyGameAndUserForLastResult(@Param("userId") Long userId, @Param("studyId") Long studyId);
}
