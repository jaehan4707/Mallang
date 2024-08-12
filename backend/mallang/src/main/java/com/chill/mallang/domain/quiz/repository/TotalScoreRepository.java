package com.chill.mallang.domain.quiz.repository;

import com.chill.mallang.domain.quiz.dto.response.TeamRankResponse;
import com.chill.mallang.domain.quiz.model.TotalScore;
import com.chill.mallang.domain.user.model.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TotalScoreRepository extends JpaRepository<TotalScore, Long> {

    @Query(value = "SELECT SUM(t.total_score) FROM total_score t " +
            "WHERE t.area = :areaID AND DATE(t.created_at) = CURDATE() " +
            "GROUP BY t.faction ", nativeQuery = true)
    List<Float> findTotalScoreByAreaID(@Param("areaID") Long areaID);

    @Query(value = "SELECT SUM(t.total_score) FROM total_score t " +
            "WHERE t.area = :areaID AND DATE(t.created_at) = CURDATE() " +
            "AND t.faction = :factionID ", nativeQuery = true)
    List<Float> findTotalScoreByAreaIDAndFactionID(@Param("areaID") Long areaID, @Param("factionID") Long factionID);
    //    @Query(value = "SELECT u.nickname AS nickName, t.total_score AS totalScore " +
//            "FROM total_score t " +
//            "JOIN user u ON t.user = u.id " +
//            "WHERE t.area = :areaID AND t.faction = :factionID AND DATE(t.created_at) = CURDATE() " +
//            "ORDER BY t.total_score DESC " +
//            "LIMIT 3", nativeQuery = true)
//    List<TeamRankResponse> findTop3(@Param("areaID") Long areaID, @Param("factionID") Long factionID);
    @Query("SELECT new com.chill.mallang.domain.quiz.dto.response.TeamRankResponse(u.nickname, t.totalScore) " +
            "FROM TotalScore t JOIN User u ON t.user.id = u.id " +
            "WHERE t.area.id = :areaID AND t.faction.id = :factionID AND FUNCTION('DATE', t.created_at) = CURRENT_DATE " +
            "ORDER BY t.totalScore DESC")
    List<TeamRankResponse> findTop3(@Param("areaID") Long areaID, @Param("factionID") Long factionID, Pageable pageable);

    default List<TeamRankResponse> findTop3Results(Long areaID, Long factionID) {
        Pageable pageable = PageRequest.of(0, 3); // 첫 번째 페이지, 3개의 결과
        return findTop3(areaID, factionID, pageable);
    }

    // areaId와 factionId를 주면 최고 득점자 id를 뱉어내는 쿼리
    @Query(value = "SELECT t.user FROM total_score t " +
            "WHERE t.area = :areaId AND DATE(t.created_at) = CURDATE() " +
            "AND t.faction = :factionId " +
            "ORDER BY t.total_score DESC LIMIT 1", nativeQuery = true)
    Long findTopUserByAreaIdAndFactionId(Long areaId, Long factionId);

    @Query(value = "SELECT * FROM ( " +
            "    SELECT ts.*, " +
            "           ROW_NUMBER() OVER (PARTITION BY ts.user ORDER BY ts.total_score DESC, ts.created_at DESC) as rn " +
            "    FROM total_score ts " +
            "    WHERE ts.area = :areaId " +
            "    AND ts.faction = :factionId " +
            "    AND DATE(ts.created_at) = CURDATE() " +
            ") ranked " +
            "WHERE ranked.rn = 1 " +
            "ORDER BY ranked.total_score DESC",
            nativeQuery = true)
    List<TotalScore> findByAreaAndFactionWithHighestScore(
            @Param("areaId") Long areaId,
            @Param("factionId") Long factionId);
}
