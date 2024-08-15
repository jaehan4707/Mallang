package com.chill.mallang.domain.area.controller.v1;

import com.chill.mallang.domain.area.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/areas")
@Tag(name = "Area API", description = "점령지 API")
public class AreaController {

    private final TryCountService tryCountService;
    private final AreaTopUserService areaTopUserService;
    private final ChallengeRecordService challengeRecordService;
    private final AllAreaService allAreaService;
    private final AreaStatusService areaStatusService;
    private final DailyService dailyService;

    @Operation(summary = "전체 점령지 단순 조회", description = "전체 점령지의 정보를 조회합니다.")
    @GetMapping()
    public ResponseEntity<?> getAllAreas() {
        return new ResponseEntity<>(allAreaService.getAllAreas(), HttpStatus.OK);
    }

    @Operation(summary = "특정 점령지 단순 조회", description = "특정 점령지의 정보를 조회합니다.")
    @GetMapping("/{areaId}")
    public ResponseEntity<?> getAllAreas(@PathVariable Long areaId) {
        return new ResponseEntity<>(allAreaService.getAreaById(areaId), HttpStatus.OK);
    }

    @Operation(summary = "점령 현황 조회", description = "양 팀의 점령지 점령 현황을 조회합니다.")
    @GetMapping("/status")
    public ResponseEntity<?> getAreaStatus() {
        return new ResponseEntity<>(areaStatusService.getAreaStatus(), HttpStatus.OK);
    }

    @Operation(summary = "점령지 대표 유저 정보 조회", description = "특정 점령지에 대한 양 팀의 정보와 각 팀 1위 유저 정보를 조회합니다.")
    @GetMapping("/{area}/{userTeam}")
    public ResponseEntity<?> getAreaInfo(@PathVariable Long area, @PathVariable Long userTeam) {
        return new ResponseEntity<>(areaTopUserService.getAreaInfo(area, userTeam), HttpStatus.OK);
    }

    @Operation(summary = "사용자 잔여 도전 횟수 조회", description = "특정 사용자의 점령 잔여 도전가능 횟수를 조회합니다.")
    @GetMapping("/try-count/{userId}")
    public ResponseEntity<?> getUserTryCountById(@PathVariable Long userId) {
        return new ResponseEntity<>(tryCountService.getUserTryCountById(userId), HttpStatus.OK);
    }

    @Operation(summary = "도전 기록 조회", description = "특정 점령지에 대한 현재 사용자의 점수정보를 조회합니다. / 양 팀 멤버들의 점수정보를 등수 오름차순으로 정렬하여 조회합니다.")
    @GetMapping("/records/{areaId}/{userId}")
    public ResponseEntity<?> getGameRecords(@PathVariable Long areaId, @PathVariable Long userId) {
        return new ResponseEntity<>(challengeRecordService.getChallengeRecord(areaId, userId), HttpStatus.OK);
    }

    @GetMapping("/daily")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok().body(dailyService.dailyTopUser());
    }
}