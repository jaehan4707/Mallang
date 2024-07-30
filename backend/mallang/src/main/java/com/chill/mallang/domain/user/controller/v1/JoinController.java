package com.chill.mallang.domain.user.controller.v1;

import com.chill.mallang.domain.user.dto.JoinRequestDTO;
import com.chill.mallang.domain.user.dto.JoinResponseDTO;
import com.chill.mallang.domain.user.service.JoinService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@ResponseBody
@RequestMapping("api/v1/user")
@Tag(name = "Join API", description = "회원가입 API")
public class JoinController {
    private static final Logger logger = LoggerFactory.getLogger(JoinController.class);
    private final JoinService joinService;

    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }

    @Operation(summary = "회원가입 api", description = "헤더에 Bearer 토큰 포함시켜주세요.")
    @PostMapping("/join")
    // 컨트롤러에 서비스 부분 다빼기
    public ResponseEntity<?> joinProcess(@Valid @RequestBody JoinRequestDTO joinRequestDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String token, HttpServletResponse response) throws IOException {
        Map<String, Object> serviceResponse = joinService.joinProcess(joinRequestDTO, token);
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }
}
