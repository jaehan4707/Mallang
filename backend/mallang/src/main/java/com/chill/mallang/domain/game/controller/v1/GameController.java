package com.chill.mallang.domain.game.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/game")
@RestController
@Tag(name = "Game API", description = "게임 관련 API")
public class GameController {

    @GetMapping
    @Operation(summary = "게임 조회", description = "게임 PK 값을 이용해 게임 조회")
    public ResponseEntity<?> getGame(@RequestBody long game_id) {
        return null;
    }
}
