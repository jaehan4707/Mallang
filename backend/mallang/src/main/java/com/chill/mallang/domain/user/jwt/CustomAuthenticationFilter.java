package com.chill.mallang.domain.user.jwt;

import com.chill.mallang.domain.user.dto.CustomUserDetails;
import com.chill.mallang.domain.user.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//로그인 및 회원가입시 자격 인증 필터
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetails customUserDetails;

    private CustomUserDetailsService userDetailsService;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService){
        this.authenticationManager = authenticationManager;
        this.userDetailsService = customUserDetailsService;
    }





}
