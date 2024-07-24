package com.chill.mallang.domain.user.jwt;


import com.chill.mallang.domain.user.dto.CustomUserDetails;
import com.chill.mallang.domain.user.dto.LoginRequest;
import com.chill.mallang.domain.user.oauth.CustomOAuthToken;
import com.chill.mallang.domain.user.service.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Iterator;


public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Autowired
    private CustomUserDetails userDetails;


    private CustomUserDetailsService userDetailsService;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = customUserDetailsService;
        setFilterProcessesUrl("/login");
    }

    // 예외 처리 위한 클래스
    public class MissingCredentialsException extends AuthenticationException {
        public MissingCredentialsException(String msg) {
            super(msg);
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // JSON 데이터를 파싱하여 email과 token을 가져옵니다.
            System.out.printf("시작");
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.printf("objectMapper :"+ objectMapper);
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            System.out.printf("loginRequest :" + loginRequest);
            String loginRequestEmail = loginRequest.getEmail();
            String loginRequestToken = loginRequest.getToken();
            System.out.printf("login" + loginRequestEmail);
            if (loginRequestEmail == null ||loginRequestToken == null) {
                throw new MissingCredentialsException("이메일 또는 OAuth 토큰이 누락되었습니다.");
            }

            CustomOAuthToken authToken = new CustomOAuthToken(loginRequestEmail, loginRequestToken);
            return authenticationManager.authenticate(authToken);
        } catch (IOException | java.io.IOException e) {
            throw new RuntimeException("요청을 처리하는 동안 오류가 발생했습니다.", e);
        }
    }

    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();
        long secondsInAYear = 365L * 24 * 60 * 60;
        long tokenValidityInSeconds = 150L * secondsInAYear;
        String token = jwtUtil.createJwt(username, role, 60 * 60 * 10L);
        response.addHeader("Authorization", "Bearer " + token);
    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }
}
