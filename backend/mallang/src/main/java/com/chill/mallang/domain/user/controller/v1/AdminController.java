package com.chill.mallang.domain.user.controller.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//테스트용 이따 지우기
@RestController
@RequestMapping("api/v1/user")
public class AdminController {
    @GetMapping("/admin")
    public String adminP(){
        return "ADMIN";
    }

}
