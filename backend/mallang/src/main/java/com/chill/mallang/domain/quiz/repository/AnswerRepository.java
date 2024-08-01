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


}
