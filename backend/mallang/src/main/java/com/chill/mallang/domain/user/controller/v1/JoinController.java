package com.chill.mallang.domain.user.controller.v1;

import com.chill.mallang.domain.user.dto.JoinDTO;
import com.chill.mallang.domain.user.service.JoinService;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping("api/v1/user")
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {

        this.joinService = joinService;
    }
    @GetMapping("/join")
    //API 확인용
    public String AA(){
        return "보기";
    }

    @PostMapping("/join")
    public String joinProcess(JoinDTO joinDTO) {
        joinService.joinProcess(joinDTO);
        return "ok";
    }

    @PostMapping("/test")
    public String test(){
        return null;
    }

}
