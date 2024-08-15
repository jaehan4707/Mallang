package com.chill.mallang.domain.area.service;

import com.chill.mallang.domain.area.repository.AreaRepository;
import com.chill.mallang.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
public class DailyService {
    private final AreaRepository areaRepository;
    private final UserRepository userRepository;
    public Map<String,Object> dailyTopUser(){
        LocalDateTime startDate = LocalDate.now().minusDays(1).atStartOfDay();
        LocalDateTime endDate = LocalDate.now().atStartOfDay();

        // 점령지 최고점 뽑기
        List<Object[]> malTeamTopUser = areaRepository.teamTopUser(1);
        List<Object[]> rangTeamTopUser = areaRepository.teamTopUser(2);

        List<Object[]> malSumScore = areaRepository.findSumScoresByAreaForYesterday(1, startDate, endDate);
        List<Object[]> rangSumScore = areaRepository.findSumScoresByAreaForYesterday(2, startDate, endDate);

        Map<String, Object> area = new LinkedHashMap<>();
        List<Object> totalMal = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Map<String, Object> mal = new HashMap<>();

            int result = checkVictory(malSumScore.get(i), rangSumScore.get(i));
            mal.put("area", malSumScore.get(i)[1]);
            // 최고 득점자
            mal.put("victory team", result);
            mal.put("team mal totalScore", sliceDouble((Double)malSumScore.get(i)[2]));
            mal.put("team rang totalScore", sliceDouble((Double)rangSumScore.get(i)[2]));
            // 말 승
            if(result == 1){
                mal.put("top score", sliceFloat((float)(malTeamTopUser.get(i)[2])));
                mal.put("top score user", userRepository.getUserById((Long) malTeamTopUser.get(i)[5]).getNickname());
            }else if(result == 2){
                mal.put("top score", sliceFloat((float)(rangTeamTopUser.get(i)[2])));
                mal.put("top score user", userRepository.getUserById((Long) rangTeamTopUser.get(i)[5]).getNickname());
            }
            totalMal.add(mal);
        }
        area.put("data",totalMal);
        return area;
    }
    public float sliceFloat(float value){
        return Math.round(value * 100) / 100.0f;
    }
    public float sliceDouble(double value){
        return Math.round(value * 100) / 100.0f;
    }
    public int checkVictory(Object[] mal, Object[] rang) {
        float malScore = ((Number) mal[2]).floatValue();
        float rangScore = ((Number) rang[2]).floatValue();

        // 말팀 승
        if (malScore > rangScore) {
            return 1;
        }
        // 랑팀 승
        if (malScore < rangScore) {
            return 2;
        }
        // 비긴 경우
        return 0;

    }
}
