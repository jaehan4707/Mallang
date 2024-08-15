package com.chill.mallang.domain.study.controller.v1;

import com.chill.mallang.domain.study.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/study")
@Tag(name = "Study API",description = "학습 API")
@RequiredArgsConstructor
public class StudyGameController {
    private final GameService gameService;

    @Operation(summary = "스터디 문제 요청", description = "스터디 게임 문제를 보여줍니다.")
    @GetMapping("/game/{userId}")
    public ResponseEntity<?> startStudyGame(@PathVariable Long userId) {
        return new ResponseEntity<>(gameService.startGame(userId), HttpStatus.OK);
    }

    @Operation(summary = "스터디 문제 제출", description = "스터디 게임 문제를 보여줍니다.")
    @PostMapping("/game/{userId}/{studyId}/{answer}")
    public ResponseEntity<?> submitAnswerStudyGame(@PathVariable Long userId,@PathVariable Long studyId,@PathVariable Long answer) {
        return new ResponseEntity<>(gameService.submitGame(userId, studyId, answer), HttpStatus.OK);
    }

    @Operation(summary = "스터디 문제 최종요청", description = "스터디 게임 보기의 단어 뜻을 제공합니다.")
    @GetMapping("/game/{userId}/{studyId}")
    public ResponseEntity<?> showResultStudyGame(@PathVariable Long userId,@PathVariable Long studyId) {
        return new ResponseEntity<>(gameService.showResultGame(userId, studyId), HttpStatus.OK);
    }

}
