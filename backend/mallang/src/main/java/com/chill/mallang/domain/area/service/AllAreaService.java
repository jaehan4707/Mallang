package com.chill.mallang.domain.area.service;

import com.chill.mallang.domain.area.dto.APIresponse;
import com.chill.mallang.domain.area.dto.AllAreaDTO;
import com.chill.mallang.domain.area.model.Area;
import com.chill.mallang.domain.area.repository.AreaRepository;
import com.chill.mallang.errors.exception.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AllAreaService {

    @Autowired
    private AreaRepository areaRepository;

    // 점령지 조회
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
}
