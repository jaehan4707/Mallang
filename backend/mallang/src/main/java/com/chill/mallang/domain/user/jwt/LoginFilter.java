package com.chill.mallang.domain.user.jwt;

import com.chill.mallang.domain.user.dto.LoginRequestDTO;
import com.chill.mallang.domain.user.oauth.CustomOAuthToken;
import com.chill.mallang.domain.user.service.CustomUserDetailsService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {
    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);
    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public LoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager, JWTUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        super(defaultFilterProcessesUrl);
        setAuthenticationManager(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        try {
            // 요청 본문을 읽어 LoginRequest 객체로 변환
            LoginRequestDTO loginRequest = new ObjectMapper().readValue(request.getInputStream(), new TypeReference<LoginRequestDTO>(){});

            // 디버깅 로그 추가
            logger.info("Received login request: " + loginRequest);

            String email = loginRequest.getEmail();
            String token = loginRequest.getToken();

            // ID 토큰을 CustomOAuthToken으로 래핑하여 인증 매니저에 전달
            return getAuthenticationManager().authenticate(new CustomOAuthToken(token));
        } catch (IOException e) {
            // JSON 파싱 오류 처리
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid request body");
            return null;
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        long secondsInAYear = 365L * 24 * 60 * 60;
        long tokenValidityInSeconds = 150L * secondsInAYear;
        String jwtToken = jwtUtil.createJwt(authResult.getName(), "ROLE_USER", tokenValidityInSeconds);
        response.setHeader("Authorization", "Bearer " + jwtToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Authentication failed: " + failed.getMessage());
    }
}
