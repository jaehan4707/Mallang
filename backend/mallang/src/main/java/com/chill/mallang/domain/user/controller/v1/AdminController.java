package com.chill.mallang.domain.user.controller.v1;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//테스트용 이따 지우기
@RestController
@RequestMapping("api/v1/user")
@Tag(name = "로그인 유저 인가", description = "인증 유저 제한 페이지 접근 확인용")
public class AdminController {
    @GetMapping("/admin")
    public String adminP(){
        return "잘되나";
    }

}
