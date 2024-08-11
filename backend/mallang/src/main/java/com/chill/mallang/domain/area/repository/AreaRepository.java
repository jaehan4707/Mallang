package com.chill.mallang.domain.area.repository;

import com.chill.mallang.domain.area.model.Area;
import com.chill.mallang.domain.quiz.model.TotalScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {

    @Query("SELECT t FROM TotalScore t " +
            "WHERE t.faction.id = :factionId " +
            "AND t.created_at BETWEEN :startDate AND :endDate " +
            "AND t.totalScore > 0 " +
            "AND t.totalScore = (SELECT MAX(ts.totalScore) FROM TotalScore ts WHERE ts.faction.id = :factionId AND ts.area.id = t.area.id) " +
            "ORDER BY t.area.id, t.totalScore DESC")
    List<TotalScore> findTopScoresByAreaAndFaction(@Param("factionId") long factionID,
                                                   @Param("startDate") LocalDateTime startDate,
                                                   @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT * FROM total_score t " +
            "WHERE t.created_at BETWEEN DATE_SUB(CURDATE(), INTERVAL 1 DAY) AND CURDATE() " +
            "AND t.total_score = (" +
            "    SELECT MAX(ts.total_score) " +
            "    FROM total_score ts " +
            "    WHERE ts.faction = :factionId " +
            "    AND ts.area = t.area " +
            "    AND ts.created_at BETWEEN DATE_SUB(CURDATE(), INTERVAL 1 DAY) AND CURDATE()" +
            ") " +
            "GROUP BY t.area",
            nativeQuery = true)
    List<Object[]> teamTopUser(@Param("factionId") long factionId);



//    @Query("SELECT t FROM TotalScore t WHERE t.faction.id = :factionId AND t.totalScore = " +
//            "(SELECT MAX(ts.totalScore) FROM TotalScore ts WHERE ts.faction.id = :factionId AND ts.area.id = t.area.id) " +
//            "AND t.created_at BETWEEN :startDate AND :endDate " +
//            "GROUP BY t.area.id")
//    List<TotalScore> findTopScoresByAreaAndFaction(@Param("factionId") long factionID,
//                                                   @Param("startDate") LocalDateTime startDate,
//                                                   @Param("endDate") LocalDateTime endDate);


    @Query("SELECT t.area.id, t.area.name, SUM(t.totalScore) " +
            "FROM TotalScore t " +
            "WHERE t.created_at BETWEEN :startDate AND :endDate " +
            "AND t.faction.id = :factionId " +
            "GROUP BY t.area.id")
    List<Object[]> findSumScoresByAreaForYesterday(@Param("factionId") long factionId,
                                                   @Param("startDate") LocalDateTime startDate,
                                                   @Param("endDate") LocalDateTime endDate

    );


}
