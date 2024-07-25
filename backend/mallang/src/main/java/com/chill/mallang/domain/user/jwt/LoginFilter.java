package com.chill.mallang.domain.user.jwt;


import com.chill.mallang.domain.user.dto.CustomUserDetails;
import com.chill.mallang.domain.user.dto.LoginRequest;
import com.chill.mallang.domain.user.oauth.CustomOAuthToken;
import com.chill.mallang.domain.user.service.CustomUserDetailsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Iterator;


public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);
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
            logger.info("시작");
            ObjectMapper objectMapper = new ObjectMapper();

            // 요청 데이터 로깅
            String requestBody = new String(request.getInputStream().readAllBytes());
            logger.info("Request Body: " + requestBody);

            // JSON 데이터 파싱
            LoginRequest loginRequest = objectMapper.readValue(requestBody, LoginRequest.class);
            logger.info("loginRequest success");
            String loginRequestEmail = loginRequest.getEmail();
            String loginRequestToken = loginRequest.getToken();
            logger.info("logintoken : " + loginRequestToken);

            if (loginRequestEmail == null || loginRequestToken == null) {
                throw new MissingCredentialsException("이메일 또는 OAuth 토큰이 누락되었습니다.");
            }

            CustomOAuthToken authToken = new CustomOAuthToken(loginRequestEmail, loginRequestToken);
            logger.info("authToken :" + authToken);
            return authenticationManager.authenticate(authToken);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 파싱 중 오류가 발생했습니다. 입력 데이터를 확인하세요.", e);
        } catch (IOException e) {
            throw new RuntimeException("요청을 처리하는 동안 오류가 발생했습니다.", e);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }

    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        logger.info("detail :"+userDetails);
        String username = userDetails.getUsername();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(username, role, 60 * 60 * 10L);
        response.addHeader("Authorization", "Bearer " + token);
    }

    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }
}
