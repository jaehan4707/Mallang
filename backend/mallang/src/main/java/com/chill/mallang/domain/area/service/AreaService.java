package com.chill.mallang.domain.area.service;

import com.chill.mallang.domain.area.dto.AreaDTO;
import com.chill.mallang.domain.area.model.Area;
import com.chill.mallang.domain.area.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AreaService {
    @Autowired
    private AreaRepository areaRepository;

    // 점령지 전체 조회
    public List<AreaDTO> getAllAreas() {
        return areaRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // 특정 점령지 조회
    public AreaDTO getAreaById(Long id) {
        Optional<Area> area = areaRepository.findById(id);
        return area.map(this::convertToDto).orElse(null);
    }

    //DTO 변환
    private AreaDTO convertToDto(Area area) {
        return AreaDTO.builder()
                .id(area.getId())
                .name(area.getName())
                .latitude(area.getLatitude())
                .longitude(area.getLongitude())
                .user(area.getUser())
                .areaLogs(area.getAreaLogs())
                .build();
    }
}
