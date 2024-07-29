package com.chill.mallang.domain.area.service;

import com.chill.mallang.domain.area.dto.APIresponse;
import com.chill.mallang.domain.area.dto.AllAreaDTO;
import com.chill.mallang.domain.area.model.Area;
import com.chill.mallang.domain.area.repository.AreaRepository;
import com.chill.mallang.domain.user.dto.TryCountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AllAreaService {

    @Autowired
    private AreaRepository areaRepository;

    // 점령지 조회
    public APIresponse<AllAreaDTO> getAreaById(Long areaId) {
        Optional<Area> area = areaRepository.findById(areaId);
        AllAreaDTO data = area.map(this::convertToDto).orElse(null);
        return APIresponse.<AllAreaDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(data)
                .build();
    }

    //DTO 변환
    private AllAreaDTO convertToDto(Area area) {
        return AllAreaDTO.builder()
                .areaId(area.getId())
                .areaName(area.getName())
                .latitude(area.getLatitude())
                .longitude(area.getLongitude())
                .build();
    }
}
