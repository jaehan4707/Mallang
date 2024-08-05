package com.chill.mallang.domain.faction.service;

import com.chill.mallang.domain.area.model.Area;
import com.chill.mallang.domain.area.service.ChallengeRecordService;
import com.chill.mallang.domain.user.model.User;
import com.chill.mallang.domain.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FactionRatioService {
    private static final Logger logger = LoggerFactory.getLogger(FactionRatioService.class);


    @Autowired
    UserRepository userRepository;

    public Map<String,Object> getFactionRatio(){

        List<User> users = userRepository.findAll();

        // 각 팀을 선택한 사용자 수
        int mal = 0;
        int lang = 0;

        for (User user : users) {
            int selectFaction = (int)user.getFaction().getId();
            if (selectFaction == 1) {
                mal += 1;
            } else if (selectFaction == 2) {
                lang += 1;
            }
        }

        logger.info("mal team : " + String.valueOf(mal));
        logger.info("lang tema : " + String.valueOf(lang));


        // 선택 비율 계산
        float mal_ratio = ((float) mal / (mal + lang)) * 100;
        float lang_ratio = 100 - mal_ratio;

        ArrayList<Map> factionInfo = new ArrayList<>();

        //faction Map에 넣기
        Map<String,Object> malFaction = new HashMap<>();
        malFaction.put("factionName","말");
        malFaction.put("factionRatio",mal_ratio);

        Map<String,Object> langFaction = new HashMap<>();
        langFaction.put("factionName","랑");
        langFaction.put("factionRatio",lang_ratio);

        factionInfo.add(malFaction);
        factionInfo.add(langFaction);

        return new HashMap<>(){{
            put("data",factionInfo);
        }};

    }
}
