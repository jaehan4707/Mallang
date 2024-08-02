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
    @Query(value = "UPDATE Answer SET check_fin = 1, updated_at = CURRENT_TIMESTAMP " +
            "WHERE userID = :userID AND quizID = :quizID " +
            "AND id = (SELECT id FROM Answer WHERE userID = :userID AND quizID = :quizID " +
            "ORDER BY created_at DESC LIMIT 1)", nativeQuery = true)
    void setAnswerTrue(@Param("userID") Long userID, @Param("quizID") Long quizID);

    // 마지막으로 제출된 쿼리 확인
    @Query("SELECT a.score FROM Answer a WHERE a.quiz.id = :quizID " +
            "ORDER BY a.updated_at DESC ")
    float findTop1AnswerScore(@Param("quizID") Long quizID);

    @Query("SELECT a " +
            "FROM Answer a " +
            "WHERE YEAR(a.created_at) = :year AND MONTH(a.created_at) = :month AND DAY(a.created_at) = :day " +
            "AND a.check_fin = 1 " +
            "AND a.id IN :quizIDs " +
            "AND a.user.id = :userID "+
            "ORDER BY a.created_at ASC")
    List<Object[]> getResultUser(@Param("year") int year,
                                 @Param("month") int month,
                                 @Param("day") int day,
                                 @Param("quizIDs") Long[] quizIDs,
                                 @Param("userID") Long userID);


    //오늘 날짜와 created_at의 날짜 데이터가 같을 것
    //check_Fin = 1
    //한 유저당 하나의 answer만 조회하되, score가 높은 데이터를 조회함. score가 같다면 answer_time이 적게 든 데이터.

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
