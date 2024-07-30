package com.chill.mallang.domain.area.service;

import com.chill.mallang.domain.area.dto.APIresponse;
import com.chill.mallang.domain.area.dto.AllAreaDTO;
import com.chill.mallang.domain.area.model.Area;
import com.chill.mallang.domain.area.repository.AreaRepository;
import com.chill.mallang.errors.exception.RestApiException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AllAreaService {

    @Autowired
    private AreaRepository areaRepository;

    // 특정 점령지 조회
    public Map<String, Object> getAreaById(Long areaId) {
        Optional<Area> area = areaRepository.findById(areaId);
        if (area.isPresent()) {

            AllAreaDTO areaInfo = AllAreaDTO.builder()
                    .areaId(area.get().getId())
                    .areaName(area.get().getName())
                    .latitude(area.get().getLatitude())
                    .longitude(area.get().getLongitude())
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
        if (areas.isEmpty()) {
            throw new RestApiException(AreaErrorCode.INVALID_PARAMETER);
        }

        List<AllAreaDTO> allAreaInfo = new ArrayList<>();
        for (int i = 0; i < areas.size(); i++) {
            Area area = areas.get(i);
            AllAreaDTO dto = AllAreaDTO.builder()
                    .areaId(area.getId())
                    .areaName(area.getName())
                    .latitude(area.getLatitude())
                    .longitude(area.getLongitude())
                    .build();
            allAreaInfo.add(dto);
        }

        Map<String, Object> response = new HashMap<>(){{
            put("data", allAreaInfo);
        }};
        return response;
        }
    }
