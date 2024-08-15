package com.chill.mallang.domain.quiz.repository;

import com.chill.mallang.domain.quiz.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    // 최종 제출 쿼리
    @Modifying
    @Query(value = "UPDATE answer SET check_fin = 1, updated_at = CURRENT_TIMESTAMP " +
            "WHERE userID = :userID AND quizID = :quizID " +
            "AND id = (SELECT id FROM answer WHERE userID = :userID AND quizID = :quizID " +
            "ORDER BY created_at DESC LIMIT 1)", nativeQuery = true)
    void setAnswerTrue(@Param("userID") Long userID, @Param("quizID") Long quizID);

    // 마지막으로 제출된 쿼리 확인
    @Query(value = "SELECT a.score " +
            "FROM answer a " +
            "WHERE a.quizID = :quizID " +
            "ORDER BY a.updated_at " +
            "DESC LIMIT 1", nativeQuery = true)
    Float findTop1AnswerScore(@Param("quizID") Long quizID);

    @Modifying
    @Query(value = "SELECT a1.* " +
            "FROM answer a1 " +
            "JOIN ( " +
            "    SELECT a2.userID, MAX(a2.score) AS max_score, MIN(a2.answer_time) AS min_time " +
            "    FROM answer a2 " +
            "    WHERE a2.area = :areaId " +
            "      AND a2.check_fin = 1 " +
            "      AND DATE_FORMAT(a2.created_at, '%Y-%m-%d') = DATE_FORMAT(CURRENT_DATE, '%Y-%m-%d') " +
            "    GROUP BY a2.userID " +
            ") max_results " +
            "ON a1.userID = max_results.userID " +
            "   AND a1.score = max_results.max_score " +
            "   AND a1.answer_time = max_results.min_time " +
            "WHERE a1.area = :areaId " +
            "  AND a1.check_fin = 1 " +
            "  AND DATE_FORMAT(a1.created_at, '%Y-%m-%d') = DATE_FORMAT(CURRENT_DATE, '%Y-%m-%d') " +
            "ORDER BY a1.score DESC, a1.answer_time ASC", nativeQuery = true)
    List<Answer> findByAreaId(@Param("areaId") Long areaId);
}
