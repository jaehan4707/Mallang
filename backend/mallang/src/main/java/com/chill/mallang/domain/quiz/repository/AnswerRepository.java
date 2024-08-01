package com.chill.mallang.domain.quiz.repository;

import com.chill.mallang.domain.quiz.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    //오늘 날짜와 created_at의 날짜 데이터가 같을 것
    //check_Fin = 1
    //한 유저당 하나의 answer만 조회하되, score가 높은 데이터를 조회함. score가 같다면 answer_time이 적게 든 데이터.

    @Query(value = """
        SELECT a1.* 
        FROM answer a1 
        JOIN (
            SELECT a2.user, MAX(a2.score) AS max_score, MIN(a2.answer_time) AS min_time 
            FROM answer a2 
            WHERE a2.area = :areaId 
              AND a2.check_fin = 1 
              AND DATE_FORMAT(a2.created_at, '%Y-%m-%d') = DATE_FORMAT(CURRENT_DATE, '%Y-%m-%d') 
            GROUP BY a2.user
        ) max_results 
        ON a1.user = max_results.user 
           AND a1.score = max_results.max_score 
           AND a1.answer_time = max_results.min_time 
        WHERE a1.area = :areaId 
          AND a1.check_fin = 1 
          AND DATE_FORMAT(a1.created_at, '%Y-%m-%d') = DATE_FORMAT(CURRENT_DATE, '%Y-%m-%d') 
        ORDER BY a1.score DESC, a1.answer_time ASC
        """, nativeQuery = true)

    List<Answer> findByAreaId(@Param("areaId") Long areaId);
}
