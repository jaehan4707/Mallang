package com.chill.mallang.domain.user.controller.v1;

import com.chill.mallang.domain.user.jwt.JWTUtil;
import com.chill.mallang.domain.user.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api/v1/user")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

//    @GetMapping("/login")
//    public String loginP(){
//        return "ok";
//    }

}