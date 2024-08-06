package com.chill.mallang.domain.area.service;

import com.chill.mallang.domain.area.model.Area;
import com.chill.mallang.domain.area.repository.AreaRepository;
import com.chill.mallang.domain.faction.model.Faction;
import com.chill.mallang.domain.faction.repository.FactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AreaStatusService {
    @Autowired
    AreaRepository areaRepository;

    @Autowired
    FactionRepository factionRepository;

    public Map<String,Object> getAreaStatus(){
        List<Area> areas = areaRepository.findAll();
        List< Faction> factions = factionRepository.findAll();

        // 각 팀의 점령지 수
        int mal = 0;
        int lang = 0;

        for (Area area : areas) {
            int areaOwner = (int) area.getUser().getFaction().getId();
            if (areaOwner == 1) {
                mal += 1;
            } else if (areaOwner == 2) {
                lang += 1;
            }
        }

        ArrayList<Map> areaStatusInfo = new ArrayList<>();

        // 실제 정보 들어가는 Map
        for (Faction faction : factions) {
            Map<String,Object> team = new HashMap<>();
                team.put("teamId",faction.getId());
                team.put("teamName",faction.getName());
            if (faction.getId() == 1) {
                team.put("area",mal);
            } else {
                team.put("area",lang);
            }
            // 리스트에 넣기
            areaStatusInfo.add(team);
        }

        //data Map에 넣기
        return new HashMap<>(){{
            put("data",areaStatusInfo);
        }};
    }
}
