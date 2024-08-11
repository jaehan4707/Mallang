package com.chill.mallang.domain.area.service;

import com.chill.mallang.domain.area.repository.AreaRepository;
import com.chill.mallang.domain.quiz.model.TotalScore;
import com.chill.mallang.domain.user.repository.UserRepository;
import io.opencensus.trace.Link;
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

        for(int i = 0 ; i < 10 ; i++){
            System.out.println(Arrays.toString(malTeamTopUser.get(i)));
            System.out.println(Arrays.toString(rangTeamTopUser.get(i)));
            System.out.println();
        }
        List<Object[]> malSumScore = areaRepository.findSumScoresByAreaForYesterday(1, startDate, endDate);
        List<Object[]> rangSumScore = areaRepository.findSumScoresByAreaForYesterday(2, startDate, endDate);

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> area = new LinkedHashMap<>();
        Map<String, Object> totalMal = new LinkedHashMap<>();
        Map<String, Object> totalRang = new LinkedHashMap<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> mal = new HashMap<>();
            Map<String, Object> rang = new HashMap<>();

            String[] result = checkVictory(malSumScore.get(i), rangSumScore.get(i));

            // 최고 득점자
            mal.put("최고 득점자", userRepository.getUserById((Long) malTeamTopUser.get(i)[4]).getNickname());
            mal.put("최고 득점자 득점 ", malTeamTopUser.get(i)[2]);
            mal.put("승패 ", result[0]);
            mal.put("팀 전체 득점", malSumScore.get(i)[2]);

            rang.put("최고 득점자", userRepository.getUserById((Long) rangTeamTopUser.get(i)[4]).getNickname());
            rang.put("최고 득점자 득점 ", rangTeamTopUser.get(i)[2]);
            rang.put("승패 ", result[1]);
            rang.put("팀 전체 득점", rangSumScore.get(i)[2]);

            // mal과 rang을 각각 고유한 키로 area에 추가
            totalMal.put("점령지 " + (i+1), mal);
            totalRang.put("점령지 " + (i+1), rang);
        }
        area.put("말팀 목록",totalMal);
        area.put("랑팀 목록",totalRang);
        response.put("data",area);
        return response;
    }
    public String[] checkVictory(Object[] mal, Object[] rang) {
        float malScore = ((Number) mal[2]).floatValue();
        float rangScore = ((Number) rang[2]).floatValue();

        // 말팀 승
        if (malScore > rangScore) {
            return new String[]{"승", "패"};
        }
        // 랑팀 승
        if (malScore < rangScore) {
            return new String[]{"패", "승"};
        }
        // 비긴 경우
        return new String[]{"무", "무"};

    }

    public void dailyAreaResult(){

    }
}
