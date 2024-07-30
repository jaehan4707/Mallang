package com.chill.mallang.domain.area.controller.v1;

import com.chill.mallang.domain.area.dto.APIresponse;
import com.chill.mallang.domain.area.dto.AllAreaDTO;
import com.chill.mallang.domain.area.dto.AreaTopUserDTO;
import com.chill.mallang.domain.area.dto.ChallengeRecordDTO;
import com.chill.mallang.domain.area.service.AllAreaService;
import com.chill.mallang.domain.area.service.TryCountService;
import com.chill.mallang.domain.area.service.AreaTopUserService;
import com.chill.mallang.domain.area.service.ChallengeRecordService;
import com.chill.mallang.domain.user.dto.TryCountDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/areas")
@Tag(name = "Area API",description = "점령지 API")
public class AreaController {
    private static final Logger logger = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    private TryCountService tryCountService;

    @Autowired
    private AreaTopUserService areaTopUserService;

    @Autowired
    private ChallengeRecordService challengeRecordService;

    @Autowired
    private AllAreaService allAreaService;

    @Operation(summary = "점령지 대표 유저 정보 조회", description = "특정 점령지에 대한 양 팀의 정보와 각 팀 1위 유저 정보를 조회합니다.")
    @GetMapping("/{area}/{userTeam}")
    public ResponseEntity<?> getAreaInfo(@PathVariable Long area, @PathVariable Long userTeam) {
        Map<String,Object> topUserInfo = areaTopUserService.getAreaInfo(area, userTeam);
        return new ResponseEntity<>(topUserInfo, HttpStatus.OK);
    }

    @Operation(summary = "사용자 잔여 도전 횟수 조회", description = "특정 사용자의 점령 잔여 도전가능 횟수를 조회합니다.")
    @GetMapping("/try-count/{userId}")
    public ResponseEntity<?> getUserTryCountById(@PathVariable Long userId) {
        Map<String,Object> tryCountInfo = tryCountService.getUserTryCountById(userId);
        return new ResponseEntity<>(tryCountInfo, HttpStatus.OK);
    }

    @Operation(summary = "도전 기록 조회", description = "특정 점령지에 대한 현재 사용자의 점수정보를 조회합니다. / 양 팀 멤버들의 점수정보를 등수 오름차순으로 정렬하여 조회합니다.")
    @GetMapping("/records/{areaId}/{userId}")
    public ResponseEntity<?> getGameRecords(@PathVariable Long areaId, @PathVariable Long userId) {
        logger.info(areaId.toString());
        logger.info(userId.toString());
        Map<String,Object> challengeRecordInfo = challengeRecordService.getChallengeRecord(areaId, userId);
        return new ResponseEntity<>(challengeRecordInfo,HttpStatus.OK);
    }

    @Operation(summary = "특정 점령지 단순 조회", description = "특정 점령지의 정보를 조회합니다.")
    @GetMapping("/{areaId}")
    public ResponseEntity<?> getAllAreas(@PathVariable Long areaId) {
        Map<String,Object> areaInfo = allAreaService.getAreaById(areaId);
        return new ResponseEntity<>(areaInfo, HttpStatus.OK);
    }

    @Operation(summary = "전체 점령지 단순 조회", description = "전체 점령지의 정보를 조회합니다.")
    @GetMapping()
    public ResponseEntity<?> getAllAreas() {
        Map<String,Object> areaInfo = allAreaService.getAllAreas();
        return new ResponseEntity<>(areaInfo, HttpStatus.OK);
    }
}