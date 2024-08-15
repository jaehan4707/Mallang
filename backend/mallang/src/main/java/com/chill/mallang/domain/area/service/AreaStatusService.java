package com.chill.mallang.domain.area.service;

import com.chill.mallang.domain.area.model.Area;
import com.chill.mallang.domain.area.repository.AreaRepository;
import com.chill.mallang.domain.faction.model.Faction;
import com.chill.mallang.domain.faction.repository.FactionRepository;
import com.chill.mallang.domain.quiz.repository.TotalScoreRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AreaStatusService {
    private static final Logger logger = LoggerFactory.getLogger(AreaStatusService.class);

    private final AreaRepository areaRepository;
    private final FactionRepository factionRepository;
    private final TotalScoreRepository totalScoreRepository;

    public Map<String,Object> getAreaStatus(){
        List<Area> areas = areaRepository.findAll();
        ArrayList<Map> areaStatusInfo = new ArrayList<>();
        List< Faction> factions = factionRepository.findAll();

        // 각 팀의 점령지 수
        int mal_area = 0;
        int lang_area = 0;

        for (int i = 0; i < areas.size(); i++) {
            Area area = areas.get(i);
            Long areaId = area.getId();

            Float mal = 0F;
            Float lang = 0F;

            List<Float> malTotalScore = totalScoreRepository.findTotalScoreByAreaIDAndFactionID(areaId, 1L);
            logger.info("scoreList : " + malTotalScore.size());
            if (malTotalScore.getFirst() != null) {
                mal += malTotalScore.get(0);
            }

            List<Float> langTotalScore = totalScoreRepository.findTotalScoreByAreaIDAndFactionID(areaId, 2L);
            logger.info("scoreList : " + langTotalScore.size());
            if (langTotalScore.getFirst() != null) {
                lang += langTotalScore.get(0);
            }

            // 비교
            if (mal > lang) {
                mal_area += 1;
            } else if (lang > mal) {
                lang_area += 1;
            }
        }

        // 실제 정보 들어가는 Map
        for (Faction faction : factions) {
            Map<String,Object> team = new HashMap<>();
                team.put("teamId",faction.getId());
                team.put("teamName",faction.getName());
            if (faction.getId() == 1) {
                team.put("area",mal_area);
            } else {
                team.put("area",lang_area);
            }
            // 리스트에 넣기
            areaStatusInfo.add(team);
        }

        Map<String,Object> teams = new HashMap<>(){{
            put("teams",areaStatusInfo);
        }};

        //data Map에 넣기
        return new HashMap<>(){{
            put("data",teams);
        }};
    }
}
