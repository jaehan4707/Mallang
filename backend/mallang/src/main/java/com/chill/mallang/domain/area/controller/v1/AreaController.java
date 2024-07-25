package com.chill.mallang.domain.area.controller.v1;

import com.chill.mallang.domain.area.dto.AreaDTO;
import com.chill.mallang.domain.area.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
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


}