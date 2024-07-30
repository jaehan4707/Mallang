package com.chill.mallang.config;

import com.chill.mallang.domain.faction.model.Faction;
import com.chill.mallang.domain.faction.model.FactionType;
import com.chill.mallang.domain.faction.repository.FactionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FactionConfig {

    @Autowired
    private FactionRepository factionRepository;

    @PostConstruct
    public void loadData() {
        if (factionRepository.count() == 0) {
            factionRepository.save(new Faction(FactionType.말));
            factionRepository.save(new Faction(FactionType.랑));
        }
    }
}