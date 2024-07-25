package com.chill.mallang.domain.user.jwt;

import com.chill.mallang.domain.user.dto.JoinRequestDTO;
import com.chill.mallang.domain.user.dto.JoinResponseDTO;
import com.chill.mallang.domain.user.service.JoinService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JoinFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(JoinFilter.class);
    private final JoinService joinService;

    public JoinFilter(JoinService joinService) {
        this.joinService = joinService;
        setFilterProcessesUrl("/api/v1/user/join");
    }
    
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            // 요청 데이터 로깅
            String requestBody = new String(request.getInputStream().readAllBytes());
            logger.info("Request Body: " + requestBody);

            // JSON 데이터 파싱
            JoinRequestDTO joinRequest = objectMapper.readValue(requestBody, JoinRequestDTO.class);
            logger.info("joinRequestToken : " + joinRequest.getToken());

            // 회원가입 처리
            JoinResponseDTO joinResponse = joinService.joinProcess(joinRequest);

            // 응답 설정
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(joinResponse));
        } catch (Exception e) {
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "회원가입 중 오류가 발생했습니다.");
            } catch (java.io.IOException ex) {
                logger.error("응답 설정 중 오류가 발생했습니다.", ex);
            }
        }
    }
}
