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

    //DTO 변환 메서드
    private AreaDTO convertToDto(Area area) {
        AreaDTO areaDTO = new AreaDTO();
        areaDTO.setId(area.getId());
        areaDTO.setName(area.getName());
        areaDTO.setLatitude(area.getLatitude());
        areaDTO.setLongitude(area.getLongitude());
        areaDTO.setUser(area.getUser());
        areaDTO.setAreaLogs(area.getAreaLogs());
        return areaDTO;
    }
}
