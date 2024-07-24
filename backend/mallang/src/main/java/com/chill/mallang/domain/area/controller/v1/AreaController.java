package com.chill.mallang.domain.area.controller.v1;

import com.chill.mallang.domain.area.dto.AreaDTO;
import com.chill.mallang.domain.area.service.AreaService;
import com.chill.mallang.domain.user.dto.TryCountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/areas")
public class AreaController {
    @Autowired
    private AreaService areaService;

    // 점령지 전체 조회
    @GetMapping
    public List<AreaDTO> getAllAreas() {
        return areaService.getAllAreas();
    }

    // 특정 점령지 조회
    @GetMapping("/{id}")
    public AreaDTO getAreaById(@PathVariable Long id) {

        return areaService.getAreaById(id);
    }

    // 2. 사용자 잔여 도전 횟수 조회
    @GetMapping("/try_count/{userId}")
    public TryCountDTO getUserTryCountById(@PathVariable Long id) {
        return areaService.getUserTryCountById(id);
    }


    // 1. 점령자 대표 유저 정보 조회


    // 3. 도전 기록 조회


}