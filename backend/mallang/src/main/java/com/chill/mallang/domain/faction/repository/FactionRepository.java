package com.chill.mallang.domain.faction.repository;

import com.chill.mallang.domain.faction.model.Faction;
import org.springframework.data.jpa.repository.JpaRepository;



public interface FactionRepository extends JpaRepository<Faction, Long> {

}
