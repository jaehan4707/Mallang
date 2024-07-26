package com.chill.mallang.domain.user.jwt;

import com.chill.mallang.domain.user.dto.CustomUserDetails;
import com.chill.mallang.domain.user.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);
    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info(request.toString());

        // 특정 URL 패턴 예외 처리
        String path = request.getRequestURI();
        String fullPath = request.getRequestURL().toString();
        if (path.equals("/api/v1/user/join") || path.equals("/api/v1/user/login")) {
            logger.info("Skipping JWT filter for path: " + path);
            filterChain.doFilter(request, response);
            return;
        }

        // request에서 Authorization 헤더를 찾음
        String authorization = request.getHeader("Authorization");
        logger.info("Authorization header: " + authorization);

        // Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.substring(7);

        // 토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰에서 email 획득
        String email = jwtUtil.getEmail(token);
        String role = jwtUtil.getRole(token);

        // userEntity를 생성하여 값 set
        User user = new User();
        user.setEmail(email);
        user.setRole(role);

        // UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        // 스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        // 스프링 시큐리티 컨텍스트에 인증 정보 설정
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }
}
