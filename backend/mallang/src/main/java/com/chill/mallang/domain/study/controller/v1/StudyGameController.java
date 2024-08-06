package com.chill.mallang.domain.study.controller.v1;

import com.chill.mallang.domain.study.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/study")
public class StudyGameController {

    private final GameService gameService;

    public StudyGameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(summary = "스터디 문제 요청", description = "스터디 게임 문제를 보여줍니다.")
    @GetMapping("/game")
    public ResponseEntity<?>StartStudyGame(HttpServletRequest request) {
        Map<String, Object> response = gameService.StartGame(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
