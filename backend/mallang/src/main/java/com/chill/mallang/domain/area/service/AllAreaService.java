package com.chill.mallang.domain.area.service;

import com.chill.mallang.domain.area.dto.AllAreaDTO;
import com.chill.mallang.domain.area.model.Area;
import com.chill.mallang.domain.area.repository.AreaRepository;
import com.chill.mallang.domain.quiz.model.Answer;
import com.chill.mallang.domain.quiz.repository.AnswerRepository;
import com.chill.mallang.errors.exception.RestApiException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class AllAreaService {

    @Autowired
    private AreaRepository areaRepository;

    private AnswerRepository answerRepository;

    private Logger logger = LoggerFactory.getLogger(AllAreaService.class);

    // 특정 점령지 조회
    public Map<String, Object> getAreaById(Long areaId) {
        Optional<Area> area = areaRepository.findById(areaId);
        if (area.isPresent()) {
            // 오늘치 + fin=1인 answer 다 불러와
            List<Answer> answers = answerRepository.findByAreaId(areaId);
            // 각 팀 총점 구하고
            int mal = 0;
            int lang = 0;
            for (Answer answer : answers) {
                if (answer.getUser().getFaction().getId() == 1) {
                    mal += answer.getScore();
                } else {
                    lang += answer.getScore();
                }
            }
            // 비교
            int winTeam = 0;// 기본 점령 안된 상태
            if (mal > lang) {
                winTeam = 1;
            } else if (lang > mal) {
                winTeam = 2;
            }

            AllAreaDTO areaInfo = AllAreaDTO.builder()
                    .areaId(area.get().getId())
                    .areaName(area.get().getName())
                    .latitude(area.get().getLatitude())
                    .longitude(area.get().getLongitude())
                    .teamId(winTeam)
                    .build();

            return new HashMap<>(){{
                put("data",areaInfo);
            }};
        } else {
            throw new RestApiException(AreaErrorCode.INVALID_PARAMETER);
        }
    }

    // 전체 점령지 조회
    public Map<String, Object> getAllAreas() {
        List<Area> areas = areaRepository.findAll();
        logger.info(String.valueOf(areas));
        if (areas == null || areas.isEmpty()) {
            throw new RestApiException(AreaErrorCode.INVALID_PARAMETER);
        }

        List<AllAreaDTO> allAreaInfo = new ArrayList<>();
        for (int i = 0; i < areas.size(); i++) {
            Area area = areas.get(i);
            Long areaId = area.getId();
            // 오늘치 + fin=1인 answer 다 불러와
            List<Answer> answers = answerRepository.findByAreaId(areaId);
            // 각 팀 총점 구하고
            int mal = 0;
            int lang = 0;
            for (Answer answer : answers) {
                if (answer.getUser().getFaction().getId() == 1) {
                    mal += answer.getScore();
                } else {
                    lang += answer.getScore();
                }
            }
            // 비교
            int winTeam = 0;// 기본 점령 안된 상태
            if (mal > lang) {
                winTeam = 1;
            } else if (lang > mal) {
                winTeam = 2;
            }
            // 높은 팀을 점령팀에 저장한 뒤에 dto에 넣기
            AllAreaDTO dto = AllAreaDTO.builder()
                    .areaId(areaId)
                    .areaName(area.getName())
                    .latitude(area.getLatitude())
                    .longitude(area.getLongitude())
                    .teamId(winTeam)
                    .build();
            allAreaInfo.add(dto);
        }

        Map<String, Object> response = new HashMap<>(){{
            put("data", allAreaInfo);
        }};
        return response;
    }
}
