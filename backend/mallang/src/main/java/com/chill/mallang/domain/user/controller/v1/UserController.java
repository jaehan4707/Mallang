package com.chill.mallang.domain.user.controller.v1;

import com.chill.mallang.domain.user.model.User;
import com.chill.mallang.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/user")
@Tag(name = "User API", description = "사용자 조회 API")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
    @Operation(summary="user 정보 조회 with id", description = "해당 유저 정보 조회 API,헤더에 토큰 첨부 필수")
    @GetMapping("/id/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }
    @Operation(summary="user 정보 조회 with email", description = "해당 유저 정보 조회 API,헤더에 토큰 첨부 필수")
    @GetMapping("/email/{email}")
    public Optional<User> getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }
    @Operation(summary="email 중복 조회", description = "email 중복 API,헤더에 토큰 첨부 필수")
    @GetMapping("/exists/email/{email}")
    public boolean checkEmailExists(@PathVariable String email) {
        return userService.existsByEmail(email);
    }
    @Operation(summary="user 유무 조회", description = "유저 존재 확인 API,헤더에 토큰 첨부 필수")
    @GetMapping("/exists/id/{id}")
    public ResponseEntity<Void> checkUserExistsById(@PathVariable Long id) {
        return ResponseEntity.ok().build();
    }
    @Operation(summary="user 정보 조회 with nickname", description = "해당 유저 정보 조회 API,헤더에 토큰 첨부 필수")
    @GetMapping("/nickname/{nickname}")
    public Optional<User> getUserByNickname(@PathVariable String nickname) {
        return userService.findByNickname(nickname);
    }

    @Operation(summary="nickname 중복 조회", description = "nickname 중복 API,헤더에 토큰 첨부 필수")
    @GetMapping("/exists/nickname/{nickname}")
    public ResponseEntity<Void> checkUserExistsByNickname(@PathVariable String nickname) {
        userService.existsByNickname(nickname);
        return ResponseEntity.ok().build(); // 상태 코드 200 OK 반환
    }
}
