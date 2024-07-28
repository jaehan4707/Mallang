package com.chill.mallang.domain.user.jwt;
import com.chill.mallang.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.chill.mallang.domain.user.dto.LoginRequestDTO;
import com.chill.mallang.domain.user.oauth.CustomOAuthToken;
import com.chill.mallang.domain.user.service.CustomUserDetailsService;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {
    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);
    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;

    public LoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager, JWTUtil jwtUtil, CustomUserDetailsService userDetailsService, UserRepository userRepository) {
        super(defaultFilterProcessesUrl);
        setAuthenticationManager(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {

        // POST 메서드가 아닌 경우 예외를 던집니다.
        if (!request.getMethod().equalsIgnoreCase("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        try {
            // 요청 본문을 읽어 LoginRequest 객체로 변환
            LoginRequestDTO loginRequest = new ObjectMapper().readValue(request.getInputStream(), new TypeReference<LoginRequestDTO>(){});

            // 디버깅 로그 추가
            logger.info("Received login request: " + loginRequest);

            String email = loginRequest.getEmail();
            String token = loginRequest.getToken();

            // ID 토큰을 CustomOAuthToken으로 래핑하여 인증 매니저에 전달
            return getAuthenticationManager().authenticate(new CustomOAuthToken(token, email));
        } catch (IOException e) {
            // JSON 파싱 오류 처리
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid request body");
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String email = (String) authResult.getPrincipal();  // 이메일 가져오기

        // 회원가입 유무 확인
        String is_registered = userRepository.existsByEmail(email)? "true" : "false";
        //jwt 토큰 생성
        Map<String, String> dataMap = new HashMap<>();
        long secondsInAYear = 365L * 24 * 60 * 60;
        long tokenValidityInSeconds = 150L * secondsInAYear;
        String jwtToken = jwtUtil.createJwt(authResult.getName(), "ROLE_USER", tokenValidityInSeconds);

        dataMap.put("token", jwtToken);
        dataMap.put("is_registered", is_registered);
        Map<String, Map<String, String>> responseMap = new HashMap<>();
        responseMap.put("data", dataMap);

        // Convert the response map to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(responseMap);

        // Write JSON to the HttpServletResponse
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
        logger.info("토큰 wrapper"+jsonResponse);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Authentication failed: " + failed.getMessage());
    }
}
