package com.chill.mallang.domain.user.controller.v1;

import com.chill.mallang.domain.user.service.UserSettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/user")
@Tag(name = "User Setting API", description = "사용자 설정 API")
public class UserSettingController {
    private static final Logger logger = LoggerFactory.getLogger(UserSettingController.class);
    private final UserSettingService userSettingService;

    public UserSettingController(UserSettingService userSettingService){
        this.userSettingService = userSettingService;
    }

    @PatchMapping("/nickname")
    @Operation(summary="user 닉네임 변경 API", description = "해당 유저 닉네임 업데이트 API,헤더에 토큰 첨부 필수")
    public ResponseEntity<?> getUserByEmail(HttpServletRequest request, @RequestBody Map<String, Object> payload) {
        String nickname = (String) payload.get("nickname");
        logger.info(request.toString());
        logger.info(nickname);
        Map<String, Object> response = userSettingService.updateNickname(request,nickname);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
