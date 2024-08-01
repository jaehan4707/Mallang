package com.chill.mallang.domain.area.service;

import com.chill.mallang.domain.area.dto.AllAreaDTO;
import com.chill.mallang.domain.area.model.Area;
import com.chill.mallang.domain.area.repository.AreaRepository;
import com.chill.mallang.domain.quiz.repository.QuizRepository;
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
    private Logger logger = LoggerFactory.getLogger(AllAreaService.class);

    @Autowired
    private QuizRepository quizRepository;

    // 특정 점령지 조회
    public Map<String, Object> getAreaById(Long areaId) {
        Optional<Area> area = areaRepository.findById(areaId);
        if (area.isPresent()) {

            Long[] quizzes = quizRepository.findQuizIdByAreaId(areaId);

            AllAreaDTO areaInfo = AllAreaDTO.builder()
                    .areaId(area.get().getId())
                    .areaName(area.get().getName())
                    .latitude(area.get().getLatitude())
                    .longitude(area.get().getLongitude())
                    .quizIds(quizzes)
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
            Long[] quizzes = quizRepository.findQuizIdByAreaId(area.getId());
            AllAreaDTO dto = AllAreaDTO.builder()
                    .areaId(area.getId())
                    .areaName(area.getName())
                    .latitude(area.getLatitude())
                    .longitude(area.getLongitude())
                    .quizIds(quizzes)
                    .build();
            allAreaInfo.add(dto);
        }

        Map<String, Object> response = new HashMap<>(){{
            put("data", allAreaInfo);
        }};
        return response;
    }
}
