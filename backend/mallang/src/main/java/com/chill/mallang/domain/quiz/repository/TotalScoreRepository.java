package com.chill.mallang.domain.quiz.repository;

import com.chill.mallang.domain.quiz.dto.response.TeamRankResponse;
import com.chill.mallang.domain.quiz.model.TotalScore;
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

}
