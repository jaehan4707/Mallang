
package com.chill.mallang.domain.faction.repository;

import com.chill.mallang.domain.faction.model.Faction;
import com.chill.mallang.domain.faction.model.FactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface FactionRepository extends JpaRepository<Faction, Long> {
    Optional<Faction> findById(Long id);
    boolean existsById(Long id);

    // 커스텀 메서드
    @Query("SELECT f FROM Faction f WHERE f.id = :id")
    Faction findFactionById(@Param("id") Long id);

    Optional<Faction> findByName(FactionType name);

}