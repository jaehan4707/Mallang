package com.chill.mallang.domain.study.controller.v1;

import com.chill.mallang.domain.study.service.GameService;
import com.chill.mallang.domain.user.service.UserSettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/study")
@Tag(name = "Study API",description = "학습 API")
public class StudyGameController {
    private static final Logger logger = LoggerFactory.getLogger(StudyGameController.class);
    private final GameService gameService;

    public StudyGameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(summary = "스터디 문제 요청", description = "스터디 게임 문제를 보여줍니다.")
    @GetMapping("/game/{userId}")
    public ResponseEntity<?> startStudyGame(@PathVariable Long userId) {
        logger.info(userId.toString());
        Map<String, Object> response = gameService.startGame(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "스터디 문제 제출", description = "스터디 게임 문제를 보여줍니다.")
    @PostMapping("/game/{userId}/{studyId}/{answer}")
    public ResponseEntity<?> submitAnswerStudyGame(@PathVariable Long userId,@PathVariable Long studyId,@PathVariable Long answer) {
        Map<String, Object> response = gameService.submitGame(userId, studyId, answer);
        logger.info(userId.toString(),studyId.toString(),answer.toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "스터디 문제 최종요청", description = "스터디 게임 보기의 단어 뜻을 제공합니다.")
    @GetMapping("/game/{userId}/{studyId}")
    public ResponseEntity<?> showResultStudyGame(@PathVariable Long userId,@PathVariable Long studyId) {
        Map<String, Object> response = gameService.showResultGame(userId, studyId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
