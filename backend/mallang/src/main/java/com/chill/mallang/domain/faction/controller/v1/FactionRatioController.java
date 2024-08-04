package com.chill.mallang.domain.faction.controller.v1;

import com.chill.mallang.domain.faction.model.Faction;
import com.chill.mallang.domain.faction.repository.FactionRepository;
import com.chill.mallang.domain.faction.service.FactionRatioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/factions")
@Tag(name = "faction API", description = "팀 API")
public class FactionRatioController {
    private static final Logger logger = LoggerFactory.getLogger(FactionRatioController.class);

    @Autowired
    FactionRatioService factionRatioService;

    @Operation(summary = "진영 선택 비율 조회", description = "사용자들의 진영 선택 비율을 조회합니다..")
    @GetMapping("/ratio")
    public ResponseEntity<?> getAllAreas() {

        Map<String,Object> factionRation = factionRatioService.getFactionRatio();
        return new ResponseEntity<>(factionRation, HttpStatus.OK);
    }
}
